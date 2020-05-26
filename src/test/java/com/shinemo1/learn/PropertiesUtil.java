package com.shinemo1.learn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public  static Properties properties =new Properties();

    static{
        try {
            InputStream inputStream =new FileInputStream(new File("src/test/resources/config.properties"));
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getExcelPath(){
        return properties.getProperty("excel.path");
    }
}
