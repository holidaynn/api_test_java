package com.shinemo1.learn;


import org.testng.annotations.DataProvider;


public class LoginCaseV3Test extends BaseProcessor{

    @DataProvider
    public  Object[][] datas(){
        return CaseUtil.getCaseDatasByApiId("1", colName);
    }


}
