package com.shinemo1.learn;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DBCheckUtil {
    /**
     *
     * @param ValidateSql 需要查询的语句
     * @return 根据脚本执行查询并返回查询结果
     */
    public static String doQuery(String ValidateSql) {

       List<DBChecker> dbCheckers= JSONObject.parseArray(ValidateSql, DBChecker.class);
       List<DBQueryResult> dbQueryResults =new ArrayList<>();
       for(DBChecker dbChecker :dbCheckers){
           String no=dbChecker.getNo();
           String sql=dbChecker.getSql();
           //执行查询
           Map<String, Object> columnLabelsAndValues= JDBCUtil.query(sql);
           DBQueryResult dbQueryResult =new DBQueryResult();
           dbQueryResult.setNo(no);
           dbQueryResult.setColumeLabelAndValues(columnLabelsAndValues);
           dbQueryResults.add(dbQueryResult);
       }
       return JSONObject.toJSONString(dbQueryResults);

    }

}
