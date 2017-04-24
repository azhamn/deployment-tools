package com.wso2telco.deployment.utils;

import com.wso2telco.deployment.Constants;

import java.io.*;
import java.io.FileReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Util {

    public static void unzip(String sourceFile, String destinationFolder) {

        try {
            String fileZip = sourceFile + ".zip";
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();

            File newFile = new File(destinationFolder);

            if (!newFile.exists()) {
                newFile.mkdir();
            }

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                newFile = new File(destinationFolder + File.separator + fileName);
                if (!zipEntry.isDirectory()) {
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                } else {
                    newFile.mkdir();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error while uncompressing file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error while uncompressing file: " + e.getMessage());
        }
    }

    public static void setDeplymentType(String serverHome, String deploymentType) {

        File fileToBeModified = new File(serverHome + "wso2server.sh");

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            //Reading all the lines of input text file into oldContent

            String line = reader.readLine();

            while (line != null) {
                oldContent = oldContent + line + System.getProperty("line.separator");

                line = reader.readLine();
            }

            //Replacing oldString with newString in the oldContent

            String newContent = oldContent.replaceAll(Constants.REGEX_DEPLOYMENT_TYPE, "-DDEPLOYMENT_TYPE=" + deploymentType + " \\\\");

            //Rewriting the input text file with newContent

            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        } catch (FileNotFoundException e) {
            System.err.println("Error while setting deployment type: "  );
        } catch (IOException e) {
            System.err.println("Error while setting deployment type: ");
        } finally {
            try {
                //Closing the resources
                reader.close();
                writer.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

}
