package com.shinemo1.learn;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class LoginCaseV2 {
    @Test(dataProvider = "datas")
    public void test(String apiIdFromCase,String parameter){
        int[] rows={2,3};
        int[] cols={1,3,4};
        Object[][]datas=ExcleUtil.datas(PropertiesUtil.getExcelPath(),"接口信息",rows,cols);
        String url="";
        String type="";
        for (Object[] objects:datas){
            type=objects[1].toString();
            String apiIdFromApi=objects[0].toString();
            if(apiIdFromCase.equals(apiIdFromApi)){
                url=objects[2].toString();
                break;

            }
        }

        //将字符串类型的数据转成json格式
        Map<String,String> params= (Map<String, String>) JSONObject.parse(parameter);
//        System.out.println(HttpUtil.postRequest(url, params));
        System.out.println(HttpUtil.doService(url, type, params));

    }
    @DataProvider
    public  Object[][] datas(){
        String filePath="src/test/resources/test_data.xlsx";
        int[] rows={2,3,4,5,6};
        int[] cols={2,4};
        Object[][] datas=ExcleUtil.datas(filePath,"用例", rows, cols);
        return datas;
//        String[] colName={"Params"};
//        Object[][] datas=CaseUtil.getCaseDatasByApiId("1", colName);
//        return  datas;
    }


}
