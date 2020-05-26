package com.shinemo1.learn;

import java.util.Map;

public class DBQueryResult {
    //脚本编号
    private String no;
    //脚本执行查到的数据，一次性保存到Map中
    private Map<String,Object> columeLabelAndValues;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Map<String, Object> getColumeLabelAndValues() {
        return columeLabelAndValues;
    }

    public void setColumeLabelAndValues(Map<String, Object> columeLabelAndValues) {
        this.columeLabelAndValues = columeLabelAndValues;
    }

    @Override
    public String toString() {
        return "DBQueryResult{" +
                "no='" + no + '\'' +
                ", columeLabelAndValues=" + columeLabelAndValues +
                '}';
    }
}
