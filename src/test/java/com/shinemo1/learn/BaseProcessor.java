package com.shinemo1.learn;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class BaseProcessor {
    public  String[] colName={"CaseId","ApiId","Params","ExpectedResponseData","PreValidateSql","AfterValidateSql"};
    @Test(dataProvider = "datas")
    public void test(String caseId,String apiId,String parameter,String expectedResponseData,String preValidateSql,String afterValidateSql){
        if(preValidateSql !=null && preValidateSql.trim().length()>0){
            preValidateSql=VariableUtil.repalceVariable(preValidateSql);
            //调用接口前查询想要验证的字段
            String preValidateResult=DBCheckUtil.doQuery(preValidateSql);
            ExcleUtil.writeBackDataList.add(new WriteBackData("用例",caseId,"PreValidateResult",preValidateResult));

        }

        //获取url
        String url= ApiUtil.getUrlByApiId(apiId);
        //获取type
        String type=ApiUtil.getTypeByApiId(apiId);

        parameter=VariableUtil.repalceVariable(parameter);
        //将字符串类型的数据转成json格式
        Map<String,String> params= (Map<String, String>) JSONObject.parse(parameter);
//        System.out.println(HttpUtil.postRequest(url, params));
        parameter=VariableUtil.repalceVariable(parameter);
        System.out.println(parameter);
        String actualResponseData=HttpUtil.doService(url, type, params);
//        ExcleUtil.writeBackData("src/test/resources/test_data.xlsx","用例",caseId,"ActualResponseData",result);
//        Assert.assertEquals(result, expectedResponseData);
        actualResponseData=AssertUtil.assertEquals(actualResponseData,expectedResponseData);
        WriteBackData writeBackData =new WriteBackData("用例",caseId,"ActualResponseData",actualResponseData);
        //保存回写数据对象
        ExcleUtil.writeBackDataList.add(writeBackData);
        if(afterValidateSql!=null&afterValidateSql.trim().length()>0){
            //调用接口后查询想要验证的字段
            afterValidateSql=VariableUtil.repalceVariable(afterValidateSql);

            String afterValidateResult=DBCheckUtil.doQuery(afterValidateSql);

            ExcleUtil.writeBackDataList.add(new WriteBackData("用例",caseId,"AfterValidateResult",afterValidateResult));
        }


    }

    @AfterSuite
    public void  batchWriteBackDatas(){

        ExcleUtil.batchWriteBackDatas(PropertiesUtil.getExcelPath());
    }
}
