package com.shinemo1.learn;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcleUtil {
    //caseId与行号的映射、列名与列号映射
    public static Map<String,Integer> rowIdentifierRownumMapping =new HashMap<>();
    public static Map<String,Integer> cellNamenumMapping=new HashMap<>();
    public static List<WriteBackData> writeBackDataList =new ArrayList<>();
    static{
        loadRownumAndCellnumMapping(PropertiesUtil.getExcelPath(),"用例");
    }

    /**
     * 获取caseid 以及它对应的行索引 、获取列名以及对应的列索引
     * @param filePath 文件路径
     * @param sheetName 表单名称
     */
    public static void loadRownumAndCellnumMapping(String filePath, String sheetName) {

        InputStream inputStream=null;
        try {
            inputStream =new FileInputStream(new File(filePath));
            Workbook workbook=WorkbookFactory.create(inputStream);
            Sheet sheet=workbook.getSheet(sheetName);
            Row titleRow=sheet.getRow(0);
            if (titleRow!=null&&!isEmptyRow(titleRow)){
                //循环处理标题行的每一列
                int lastCellnum=titleRow.getLastCellNum();
                for (int i = 0; i <lastCellnum ; i++) {
                    Cell cell=titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String title=cell.getStringCellValue();
                    title=title.substring(0, title.indexOf("("));
                    int cellnum=cell.getAddress().getColumn();
                    cellNamenumMapping.put(title, cellnum);

                }
            }
            //从第二行开始获取所有数据行
            int lastRownum=sheet.getLastRowNum();
            for (int i = 1; i <=lastRownum ; i++) {
                Row dataRow=sheet.getRow(i);
                Cell firstcCellOfRaw=dataRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                firstcCellOfRaw.setCellType(CellType.STRING);
                String caseId=firstcCellOfRaw.getStringCellValue();
                int rownum=dataRow.getRowNum();
                rowIdentifierRownumMapping.put(caseId, rownum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    //废弃 不使用该方法
    public static Object[][] datas(String filePath,String sheetName, int[] rows, int[] cols){

        Object[][] datas=null;
        try {
            //创建工作簿
            Workbook workbook= WorkbookFactory.create(new File(filePath));
            //获取sheet对象
            Sheet sheet=workbook.getSheet(sheetName);
            //定义保存数据数组的长度
            datas=new Object[rows.length][cols.length];
            //循环获取每一行
            for (int i = 0; i < rows.length; i++) {
                //根据索引取出一行
                Row row=sheet.getRow(rows[i]-1);
                for (int j = 0; j <cols.length ; j++) {
                    //获取列
                    Cell cell=row.getCell(cols[j]-1);
                    cell.setCellType(CellType.STRING);
                    String value=cell.getStringCellValue();
                    datas[i][j]=value;
                }
                
            }

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        return datas;

    }

    /**
     * 解析指定表单的数据封装为case对象
     * 1. 创建workbook 2.获取sheet表格的title数据 3.通过反射调用Case中的方法
     * @param filePath excel路径
     * @param sheetName 表单名
     */
    public static <T> List<T> loadData(String filePath, String sheetName, Class<T> clazz) {
        List<T> list=new ArrayList<>();
        try {
            //创建workbook对象
            Workbook workbook= WorkbookFactory.create(new File(filePath));
            //获取表单
            Sheet sheet=workbook.getSheet(sheetName);
            //获取行、列（通过反射封装对象）1.获取第一行的title
            Row titleRow=sheet.getRow(0);
            int lastCellNum=titleRow.getLastCellNum();//拿到最后一列的列号
            String[] titleFiles=new  String[lastCellNum];
            //循环处理第一行每一列,取出字段名保存到数组
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell=titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);//避免为空等发生奇怪问题，加个策略
                cell.setCellType(CellType.STRING);//celL为数字时处理比较麻烦，统一设置为字符串格式
                //获取列的数据
                String title=cell.getStringCellValue();
                //只获取（ 前面的部分
                title=title.substring(0, title.indexOf("("));
                titleFiles[i]=title;

            }

            //循环取出测试数据
            int lastRowIndex=sheet.getLastRowNum();//拿到行索引，从0开始
            //循环每个数据行
            for (int i = 1; i <lastRowIndex+1 ; i++) {
                T obj= clazz.newInstance();//每一行都是一个对象
                Row dataRow=sheet.getRow(i);
                if (dataRow!=null&&isEmptyRow(dataRow)){
                    continue;
                }
                //循环数据行每一列，拿到数据封装到cs对象中去
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell=dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value=cell.getStringCellValue();
                    //获得要运行的方法名
                    String methodName="set"+titleFiles[j];
                    //获得方法
                    Method method=clazz.getMethod(methodName, String.class);
                    //运行方法
                    method.invoke(obj,value);
                }
                list.add(obj);
                //将封装的对洗那个添加到准备的集合中去
//                if (obj instanceof Case){//判断obj对象类型
//                    Case cs=(Case)obj;
//                    CaseUtil.cases.add(cs);
//                }else if(obj instanceof Api){
//                    Api api=(Api)obj;
//                    ApiUtil.apis.add(api);
//                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 遍历每一行的数据,如果有一列不为空就返回false
     * @param dataRow 数据行
     * @return 返回true or false
     */
    private static boolean isEmptyRow(Row dataRow) {
        int lastCellNum=dataRow.getLastCellNum();
        for (int i = 0; i <lastCellNum ; i++) {

            Cell cell=dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String value=cell.getStringCellValue();
            if (value !=null && value.trim().length() >0){
                return  false;
            }
        }
        return true;
    }

    /**
     * 回写接口响应
     * @param filePath 用例路径
     * @param sheetname 表单名称
     * @param caseId 用例id
     * @param cellName 需要写到那个字段下
     * @param result 响应结果
     */
    public static void writeBackData(String filePath,String sheetname,String caseId, String cellName, String result) {
        int rownum= rowIdentifierRownumMapping.get(caseId);
        int cellnum=cellNamenumMapping.get(cellName);
        InputStream inputStream=null;
        OutputStream outputStream=null;
        try {
            inputStream=new FileInputStream(new File(filePath));
            Workbook workbook=WorkbookFactory.create(inputStream);
            Sheet sheet=workbook.getSheet(sheetname);
            Row row=sheet.getRow(rownum);
            Cell cell=row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(result);
            outputStream =new FileOutputStream(new File(filePath));
            workbook.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(outputStream!=null){
                   outputStream.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    public static void batchWriteBackDatas(String filePath){
        InputStream inputStream=null;
        OutputStream outputStream=null;

        try {
            inputStream=new FileInputStream(new File(filePath));
            Workbook workbook=WorkbookFactory.create(inputStream);
            for(WriteBackData writeBackData:writeBackDataList){
                //获取表单信息，数据已存在于对象里面
                String sheetName=writeBackData.getSheetName();
                Sheet sheet=workbook.getSheet(sheetName);
                String rowIdentifier=writeBackData.getRowIdentifier();
                int rownum= rowIdentifierRownumMapping.get(rowIdentifier);
                Row row =sheet.getRow(rownum);
                String cellName=writeBackData.getCellName();
                int cellnum=cellNamenumMapping.get(cellName);
                Cell cell=row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String result=writeBackData.getResult();
                cell.setCellValue(result);
            }
            outputStream=new FileOutputStream(new File(filePath));
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(inputStream!=null){
                    inputStream.close();
                }
                if(outputStream!=null){
                    outputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }



    }

    }
}
