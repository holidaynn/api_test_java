package com.shinemo1.learn;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoginCase {
    @Test(dataProvider="datas")
    public void test(String parameter){
//        System.out.println(parameter);
//        System.out.println("mobile:"+mobile+",password"+password);
        String url="https://youli.uban360.com/gift-manager/account/login";
//        Map<String,String> params=new HashMap<>();
        //将字符串类型的数据转成json格式
        Map<String,String> params= (Map<String, String>) JSONObject.parse(parameter);
        //将指定的JSON字符串转换成自己的实体类的对象
//        LoginParam loginParam=JSONObject.parseObject(parameter,LoginParam.class);
//        System.out.println(loginParam);
        System.out.println(HttpUtil.postRequest(url, params));
    }
    @DataProvider
    public  Object[][] datas(){
        String filePath=PropertiesUtil.getExcelPath();
        int[] rows={2,3,4,5,6};
        int[] cols={4};
        Object[][] datas=ExcleUtil.datas(filePath,"用例", rows, cols);
        return datas;
    }



}
