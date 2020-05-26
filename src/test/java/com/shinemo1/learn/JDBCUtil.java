package com.shinemo1.learn;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JDBCUtil {
    //解析jdbc.properties 文件
    public static Properties properties=new Properties();
    static {
        InputStream inStream;
        try {
            inStream = new FileInputStream(new File("src/test/resources/jdbc.properties"));
            properties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param sql 查询语句
     * @param values where条件字段值数组
     * @return 查询结果Map
     */
    public static Map<String, Object> query(String sql, Object... values) {

        Map<String, Object> colAndVal=null;

        try {
            Connection connection = getConnect();
            //获取数据库操作对象
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            //设置条件字段值
            for(int i=1; i< values.length+1;i++) {
                preparedStatement.setObject(i, values[i-1]);
            }
            //查询
            ResultSet resultSet=preparedStatement.executeQuery();
            //获取查询相关信息
            ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
            //获取查询sql 列数量
            int columnCount=resultSetMetaData.getColumnCount();

            //准备Map
            colAndVal=new HashMap<String, Object>();
            //拿出结果
            while(resultSet.next()) {
                for(int i=1;i<columnCount+1;i++) {
                    //获取列字段名
                    String columnLabel=resultSetMetaData.getColumnLabel(i);
                    //根据列字段名获取值
                    Object columnValue=resultSet.getObject(columnLabel);
                    colAndVal.put(columnLabel, columnValue);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colAndVal;
    }

    //获取连接
    public static Connection getConnect() throws SQLException {
        //获取数据库url、user、password
        String url=properties.getProperty("jdbc.url");
        String user=properties.getProperty("jdbc.user");
        String password=properties.getProperty("jdbc.password");

        //连接数据库
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}
