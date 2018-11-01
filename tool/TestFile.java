package com.compareFile.tool;

/**
 * @author GWF
 * @Description: TODO
 * @Title: rr
 * @ProjectName webService
 * @date 2018/11/1 18:13
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class TestFile {

    /**
     * @param args
     */
    public static void main(String[] args) {
        File folderOld = new File("D:\\file\\old");
        File folderNew = new File("D:\\file\\new");
        File folderLock = new File("D:\\file\\lock");

        File[] fielsOld = folderOld.listFiles();
        File[] fielsNew = folderNew.listFiles();
        List<File> add = new ArrayList<File>();
        List<File> modify = new ArrayList<File>();
        List<File> delete = new ArrayList<File>();
        for(int i = 0; i < fielsOld.length; i++) {
            delete.add(fielsOld[i]);
        }

        for(int i = 0; i < fielsNew.length; i++) {
            int diff = 0;

            for(int j = 0; j < fielsOld.length; j++) {
                if(fielsNew[i].getName().equals(fielsOld[j].getName())){
                    delete.remove(j);

                    // modify
                    if(fielsNew[i].lastModified() != fielsOld[j].lastModified()) {
                        modify.add(fielsNew[i]);
                    }
                }
                else {
                    diff++;
                }
            }

            // add
            if(fielsOld.length == diff) {
                add.add(fielsNew[i]);
            }
        }

        System.err.println(java.util.Arrays.toString(add.toArray()));
        System.err.println(java.util.Arrays.toString(modify.toArray()));
        System.err.println(java.util.Arrays.toString(delete.toArray()));

        if(!folderLock.exists()) {
            folderLock.mkdir();
        }

        File deleteFile = new File(folderLock.getAbsolutePath()
                + File.separatorChar + "DELETE.TXT");

        try {
            OutputStream out = new FileOutputStream(deleteFile);

            for(int i = 0; i < delete.size(); i++) {
                out.write((delete.get(i).getAbsolutePath() + "\n").getBytes());
            }

            out.flush();
            out.close();

            // put add and modify together.
            add.addAll(modify);
            for(int i = 0; i < add.size(); i++) {
                InputStream in = new FileInputStream(add.get(i));
                out = new FileOutputStream(folderLock.getAbsolutePath()
                        + File.separatorChar + add.get(i).getName());
                byte[] bytes = new byte[512];

                while(in.read(bytes) != -1) {
                    out.write(bytes);
                }

                out.flush();
                out.close();
                in.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

