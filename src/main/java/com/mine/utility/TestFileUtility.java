package com.mine.utility;

import java.io.File;
import java.io.IOException;

/**
 * @stefanl
 */
public class TestFileUtility {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread() {
                public void run() {

                    FileUtility fileHelper = FileUtility.getInstance();
                    String path = "d:\\Tests\\output\\" + this.getName() + ".txt";

                    File file = new File(path);
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String text = "Thread executed - " + this.getName();
                    System.out.println(this.getName() + " set file " + file.getPath());
                    for (int j = 0; j < 1000; j++) {
                        try {
                            System.out.println(this.getName() + " is writing: " + text + " - to " + fileHelper.getFile(path).get().getPath());
                            fileHelper.write(text, path);
                            System.out.println(this.getName() + " is reading " + fileHelper.read(path, true) + " - from " + fileHelper.getFile(path).get().getPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        }
    }
}
