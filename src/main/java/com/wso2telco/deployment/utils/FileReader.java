package com.wso2telco.deployment.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class FileReader {

    public static HashMap<String, String> readPropertyFile(String fileName) {

        HashMap<String, String> propertyList = new HashMap();

        try {
            File file = new File("config" + File.separator + fileName + ".config");
            FileInputStream input = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(input);
            input.close();

            Enumeration keys = properties.keys();

            while (keys.hasMoreElements()) {

                String key = (String) keys.nextElement();
                String value = properties.getProperty(key);
                propertyList.put(key, value);

                //System.out.println(key + " : " + value);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error reading config file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading config file: " + e.getMessage());
        }

        return propertyList;
    }
}
