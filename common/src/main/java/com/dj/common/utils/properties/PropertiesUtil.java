package com.dj.common.utils.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by daijie on 2017-8-4.
 */
public class PropertiesUtil
{
    private static Properties configProperties = getProperties("config.properties");
    private static Properties exceptionProperties = getProperties("exception_zh.properties");
    private static Properties getProperties(String filename)
    {
        Properties properties = new Properties();
        try
        {
            InputStreamReader is = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(filename), "UTF-8");
            properties.load(is);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("找不到：“" + filename + "” 配置文件！");
            e.printStackTrace();
        }

        return properties;
    }

    public static String getConfigProperty(String key)
    {
        String value = configProperties.getProperty(key);
        if ((key == null) || (value == null)) {
            try {
                throw new NoSuchPropertyException("找不到：“" + key + "” 配置属性！");
            } catch (NoSuchPropertyException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static String getExceptionProperty(String key)
    {
        String value = exceptionProperties.getProperty(key);
        if ((key == null) || (value == null)) {
            try {
                throw new NoSuchPropertyException("找不到：“" + key + "” 异常码！");
            } catch (NoSuchPropertyException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}