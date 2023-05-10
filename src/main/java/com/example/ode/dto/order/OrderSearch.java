package com.example.ode.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class OrderSearch {


    /**
     * 订单id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 下单用户名
     */
    private String name;

    /**
     * 桌号
     */
    private Integer tableId;

    /**
     * 订单状态（0：未开始，1：进行中，2：待评价，3：已完成，4：已取消）
     */
    @Max(value = 4,message = "订单状态只能是0~4")
    @Min(value = 0,message = "订单状态只能是0~4")
    private Integer status;

    /**
     * 最低金额
     */
    @DecimalMin(value = "0", message = "金额必须为正数")
    private BigDecimal minTotal;

    /**
     * 最高金额
     */
    @DecimalMin(value = "0", message = "金额必须为正数")
    private BigDecimal maxTotal;

    /**
     * 起始日
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date start;

    /**
     * 终止日
     */
    private Date end;

    /**
     * 当前页码，用于分页
     */
    @NotNull(message = "当前页码不能为空")
    @Min(value = 1,message = "当前页码必须是正数")
    private Integer pageNum;

    /**
     * 当前页大小
     */
    @NotNull(message = "当前页大小不能为空")
    @Min(value = 1,message = "当前页大小必须是正数")
    private Integer pageSize;

//    public void setStart(Date start) {
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        String dateStr=simpleDateFormat.format(start);
//        java.sql.Date sqlDate = null;
//
//        long time = 0;
//        try {
//            time = simpleDateFormat.parse(dateStr).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        sqlDate = new java.sql.Date(time);
//        this.start = sqlDate;
//    }
//
//    public void setEnd(Date end) {
//        this.end = end;
//    }

}
