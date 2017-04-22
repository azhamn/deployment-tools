package utils;

import java.io.*;
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

            if(!newFile.exists()){
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

}
