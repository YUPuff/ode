package com.example.ode.common;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description: 通用分页类
 * @Date: 2023-01-29 20:31
 **/

public class Page {

    /**
     * 当前页
     */
    private int current = 1;

    /**
     * 每页记录数
     */
    private int size = 10;

    /**
     * 数据总数，用于计算总页数
     */
    private int rows;

    //查询路径，用于页面复用链接
//    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current>=1){
            this.current = current;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size>0 && size<=100){
            this.size = size;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows>0){
            this.rows = rows;
        }
    }


    /**
     * 前端只会传入当前页的页码，需要根据当前页页码算出起始行
     * @return
     */
    public int getOffset(){
        return (current-1)*size;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotal(){
        if(rows%size == 0){
            return rows/size;
        }else{
            return rows/size+1;
        }
    }

    /**
     * 获取起始页
     * 可能总共有十页数据，但是当前页面下只能显示临近的五页页码
     * @return
     */
    public int getFrom(){
        int from = current-2;
        return from<1 ? 1 : from;
    }
    public int getTo(){
        int to = current+2;
        return to>getTotal() ? getTotal() : to;
    }

}
