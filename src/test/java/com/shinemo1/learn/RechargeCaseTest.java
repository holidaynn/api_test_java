package com.shinemo1.learn;

import org.testng.annotations.DataProvider;

public class RechargeCaseTest extends  BaseProcessor{
    @DataProvider
    public  Object[][] datas(){
        System.out.println(CaseUtil.getCaseDatasByApiId("2", colName));
        return CaseUtil.getCaseDatasByApiId("2", colName);
    }

}
