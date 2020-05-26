package com.shinemo1.learn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例工具类，加载所有测试数据
 */
public class CaseUtil {
    /**
     * 集合用于保存所有用例对象,static用于后续共享数据
     */
    public  static List<Case> cases=new ArrayList();
    //在getCaseDatasByApiId执行前需要保证cases中有数据
    static{
        //将所有数据解析封装到cases
        List<Case> caseList=ExcleUtil.loadData(PropertiesUtil.getExcelPath(),"用例",Case.class);
        cases.addAll(caseList);
    }

    /**
     * 根据接口编号获取用例数据
     * @param apiId ：接口id
     * @param colName:要获取数据对应的列名
     * @return 符合要求的二维数组
     */
    public static Object[][] getCaseDatasByApiId(String apiId,String[] colName){
        Class<Case> clazz=Case.class;
        ArrayList<Case> csList=new ArrayList<>();//保存满足条件的用例
        //将满足条件的用例放到集合中
        for(Case cs:cases){
            if(cs.getApiId().equals(apiId)){
                csList.add(cs);
            }
        }
        Object[][] datas=new Object[csList.size()][colName.length];
        //遍历集合，通过反射获取对应的值保存在object数组中
        for (int i = 0; i <csList.size() ; i++) {
            Case cs=csList.get(i);
            for (int j = 0; j <colName.length ; j++) {
                //通过反射获取对应字段的值
                String methodName="get"+colName[j];
                try {
                    Method method=clazz.getMethod(methodName);
                    String value= (String) method.invoke(cs);
                    datas[i][j]=value;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return  datas;
    }

//    public static void main(String[] args) {
//        String[] colName={"Params","Desc"};
//        Object[][] datas=getCaseDatasByApiId("1", colName);
//        for(Object[] objects :datas){
//            for (Object object:objects){
//                System.out.print(object);
//            }
//            System.out.println();
//        }
//    }
}
