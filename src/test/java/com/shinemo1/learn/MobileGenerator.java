package com.shinemo1.learn;

import java.util.Map;

//手机号生成器
public class MobileGenerator {

    //生成注册手机号
    public String generateRegisterMobile(){
        String sql="select concat(max(mobile)+1,' ') as registerMobile from gift_deal";
        Map<String,Object> columnLabelAndValue=JDBCUtil.query(sql);
        return columnLabelAndValue.get("registerMobile").toString();
    }

    //生成还未注册过的手机号
    public  String generateNotExsitRegisterMobile(){

        String sql="select concat(max(mobile)+2,'') as notExsitRegisterMobile from gift_deal";
        Map<String,Object> columnLabelAndValue=JDBCUtil.query(sql);
        return columnLabelAndValue.get("notExsitRegisterMobile").toString();
    }
}
