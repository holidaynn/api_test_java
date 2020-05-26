package com.shinemo1.learn;

import java.lang.reflect.Method;
import java.util.*;

public class VariableUtil {
    public  static Map<String,String> variableNameAndValuesMap=new HashMap<>();
    public static List<Variable> variables=new ArrayList<>();

    static {
        //第一步加载表单里的数据依次将每行封装成对象，然后统一添加到集合中
        List<Variable> variableList =ExcleUtil.loadData(PropertiesUtil.getExcelPath(), "变量", Variable.class);
        variables.addAll(variableList);
        //将变量名及变量值加载到map集合
        loadVariablesTomap();
        ExcleUtil.loadRownumAndCellnumMapping(PropertiesUtil.getExcelPath(), "变量");

    }
    //遍历Map集合，将变量名和变量值保存到map
    private static void loadVariablesTomap() {
        for(Variable variable:variables){
            String variableName=variable.getName();
            String variableValue=variable.getValue();
            //如果值为空
            if(variableValue==null||variableValue.trim().length()==0){
                //获取要反射的类和方法
                String reflectClass=variable.getReflactClass();
                String reflectMethod=variable.getReflactMethod();

                try {
                    //通过反射获取类型编码
                    Class clazz=Class.forName(reflectClass);
                    Object object=clazz.newInstance();
                    Method method=clazz.getMethod(reflectMethod);
                    variableValue= (String) method.invoke(object);
                    //保存要回写的数据到集合中
                    ExcleUtil.writeBackDataList.add(new WriteBackData("变量",variableName,"ReflactValue",variableValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            variableNameAndValuesMap.put(variableName, variableValue);
        }
    }

    public static String repalceVariable(String parameter) {
        Set<String> variableNames=variableNameAndValuesMap.keySet();
        for(String variableName:variableNames){
            if(parameter.contains(variableName)){
                parameter=parameter.replace(variableName, variableNameAndValuesMap.get(variableName));
            }
        }

        return parameter;
    }
}
