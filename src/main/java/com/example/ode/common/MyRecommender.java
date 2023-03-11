package com.example.ode.common;


import com.example.ode.constant.RecommenderConstants;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLBooleanPrefJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: lyl
 * @Date: 2023-03-09 11:07
 **/


@Slf4j
public class MyRecommender {


    @Value("${spring.datasource.username}")
    private static String username;

    @Value("${spring.datasource.password}")
    private static String password;

    private final DataModel dataModel;

    private MyRecommender(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public static MyRecommender build() throws Exception {
        DataModel model = buildJdbcDataModel();
        return new MyRecommender(model);
    }


    /**
     * dataModel 有两种结构：
     * GenericDataModel: 用户ID，物品ID，用户对物品的打分(UserID,ItemID,PreferenceValue)
     * GenericBooleanPrefDataModel: 用户ID，物品ID (UserID,ItemID)
     * 因为系统数据不含评分，故采用GenericBooleanPrefDataModel
     * @return DataModel
     */
    private static DataModel buildJdbcDataModel() throws Exception {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDatabaseName("test");
        dataSource.setServerTimezone("Asia/Shanghai");
        JDBCDataModel dataModel = new MySQLBooleanPrefJDBCDataModel(dataSource,"t_recommend",
                "user_id","dish_id","add_time");
        return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(dataModel));
    }

    /**
     * 创建基于用户的推荐器
     * @param similarityType 相似度算法类型
     * @return UserBaseRecommender
     */
    public UserBaseRecommender getUserBaseRecommender(String similarityType) throws TasteException {
        return new UserBaseRecommender(similarityType);
    }

    /**
     * 创建基于物品的推荐器
     * @param similarityType 相似度算法类型
     * @return UserBaseRecommender
     */
    public ItemBaseRecommender getItemBaseRecommender(String similarityType) throws TasteException {
        return new ItemBaseRecommender(similarityType);
    }


    /**
     * 基于用户的推荐器
     * 构造方法生成用户之间相似度，不完整
     *
     */
    public class UserBaseRecommender {

        private final UserSimilarity similarity;

        private UserNeighborhood neighborhood;

        public UserBaseRecommender(String similarityType) throws TasteException {
            similarity = getUserSimilarity(similarityType, dataModel);
        }

        private UserSimilarity getUserSimilarity(String type, DataModel model) throws TasteException {
            return (UserSimilarity) getSimilarity(type, model);
        }

        /**
         * 计算最近邻域 基于固定数量的邻居,对每个用户取固定数量N个最近邻居
         * @param num  固定邻居数量
         * @return
         */
        public void getNearestUserNeighborhood(int num) throws TasteException {
            neighborhood = new NearestNUserNeighborhood(num, similarity, dataModel);
        }

        /**
         * 计算最近邻域 基于相似阈值,对每个用户的相似度基于一定限制，取落在相似度限制以内的所有用户为邻居
         * @param threshold  最低相似阈值
         * @return
         */
        public void getThresholdUserNeighborhood(double threshold) throws TasteException {
            neighborhood = new ThresholdUserNeighborhood(threshold, similarity, dataModel);
        }

        /**
         * 获取基于用户的推荐器
         * @param pref 是否需要首选,即偏好
         * @return RecommenderBuilder
         */
        public CommonRecommender getCommonRecommender(boolean pref) throws TasteException {
            return  new CommonRecommender(pref
                    //基于用户的推荐引擎
                    ? model -> new GenericUserBasedRecommender(model, neighborhood, similarity)
                    //基于用户的无偏好值推荐引擎
                    : model -> new GenericBooleanPrefUserBasedRecommender(model, neighborhood, similarity));
        }

    }

    /**
     * 基于物品的推荐器
     * 构造方法生成物品之间相似度，不完整
     * 调用recommender方法生成完整的推荐器
     */
    public class ItemBaseRecommender {
        private final ItemSimilarity similarity;

        public ItemBaseRecommender(String similarityType) throws TasteException {
            this.similarity = getItemSimilarity(similarityType, dataModel);
        }

        /**
         * 获取基于物品的相似度量
         * @param type
         * @param model
         * @return
         * @throws TasteException
         */
        public ItemSimilarity getItemSimilarity(String type, DataModel model) throws TasteException {
            return (ItemSimilarity) getSimilarity(type, model);
        }

        public CommonRecommender getCommonRecommender(boolean pref) throws TasteException {
            return new CommonRecommender(pref
                    //基于物品的推荐引擎
                    ? model -> new GenericItemBasedRecommender(model, similarity)
                    //基于物品的无偏好值推荐引擎
                    : model -> new GenericBooleanPrefItemBasedRecommender(model, similarity));
        }
    }

    /**
     * 根据输入返回相似度量，Refreshable是根接口
     * @param type
     * @param model
     * @return
     * @throws TasteException
     */
    private static Refreshable getSimilarity(String type, DataModel model) throws TasteException {
        switch (type) {
            case RecommenderConstants.SIMILARITY_PEARSON:
                return new PearsonCorrelationSimilarity(model);
            case RecommenderConstants.SIMILARITY_COSINE:
                return new UncenteredCosineSimilarity(model);
            case RecommenderConstants.SIMILARITY_TANIMOTO:
                return new TanimotoCoefficientSimilarity(model);
            case RecommenderConstants.SIMILARITY_LOGLIKELIHOOD:
                return new LogLikelihoodSimilarity(model);
            case RecommenderConstants.SIMILARITY_CITY_BLOCK:
                return new CityBlockSimilarity(model);
            case RecommenderConstants.SIMILARITY_SPEARMAN:
                return new SpearmanCorrelationSimilarity(model);
            case RecommenderConstants.SIMILARITY_EUCLIDEAN:
            default:
                // 欧氏距离可以计算任何用户之间的相似度
                return new EuclideanDistanceSimilarity(model);
        }
    }

    /**
     * 通用的推荐器
     */
    public class CommonRecommender {
        private final RecommenderBuilder builder;
        private final Recommender recommender;

        private CommonRecommender(RecommenderBuilder builder) throws TasteException {
            this.builder = builder;
            this.recommender = builder.buildRecommender(dataModel);
        }

        public List<RecommendedItem> recommend(long userId, int howMany) throws TasteException {
            return recommender.recommend(userId, howMany);
        }

        /**
         * 计算用户的预测评分和实际评分之间的均方根（差值平方和均值的平方根）差异。
         * 会使估计值的偏离更严重
         */
        public Evaluator rmsEvaluator() {
            return new Evaluator(builder, new RMSRecommenderEvaluator());
        }

        /**
         * 计算平均差值
         */
        public Evaluator averageAbsoluteDifferenceEvaluator() {
            return new Evaluator(builder, new AverageAbsoluteDifferenceRecommenderEvaluator());
        }
    }

    /**
     * 评估推荐结果
     */
    public class Evaluator {
        private final RecommenderBuilder builder;
        private final RecommenderEvaluator recommenderEvaluator;

        private Evaluator(RecommenderBuilder builder, RecommenderEvaluator recommenderEvaluator) {
            this.builder = builder;
            this.recommenderEvaluator = recommenderEvaluator;
        }

        /**
         * 评估准确性，返回数值越低精度越高
         * 返回值越小，推荐算法越好，0是最低/最好的评估值，意味着完美匹配。
         * @param trainRatio 训练数据占比
         */
        public double evaluate(double trainRatio) throws TasteException {
            return recommenderEvaluator.evaluate(builder, null, dataModel, trainRatio, 1.0);
        }

        /**
         * 评估查准率getPrecision() 和查全率getRecall()
         *
         * @param topN
         * @return
         * @throws TasteException
         */
        public IRStatistics evaluate(int topN) throws TasteException {
            RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
            return evaluator.evaluate(builder, null, dataModel, null, topN, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        }
    }
}



