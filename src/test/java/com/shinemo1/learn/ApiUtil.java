package com.shinemo1.learn;

import java.util.ArrayList;
import java.util.List;

public class ApiUtil {
    public  static  List<Api> apis=new ArrayList<>();

    static{
        List<Api> apiList=ExcleUtil.loadData(PropertiesUtil.getExcelPath(), "接口信息",Api.class);
        apis.addAll(apiList);
    }

    public static String getUrlByApiId(String apiId) {
        for(Api api:apis){
            if(api.getApiId().equals(apiId)){
                return api.getUrl();
            }
        }
        return "";
    }

    public static String getTypeByApiId(String apiId) {
        for(Api api:apis){
            if(api.getApiId().equals(apiId)){
                return api.getType();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        for(Api api :apis){
            System.out.println(api);
        }
    }


}
