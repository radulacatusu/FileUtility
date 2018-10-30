package com.mine.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * This class is thread safe.
 */
public class FileUtility {

    private static FileUtility instance;

    /**
     * Thread safe
     *
     * @return single instance of FileUtility
     */
    public static FileUtility getInstance() {
        if (instance == null) {
            synchronized (FileUtility.class) {
                if (instance == null) {
                    instance = new FileUtility();
                }
            }
        }
        return instance;
    }

    /**
     * Proper way to get a file
     *
     * @return file
     */
    public Optional<File> getFile(String path) {
        synchronized (this) {
            return Optional.ofNullable(createFile(path));
        }
    }

    private File createFile(String path) {
        return new File(path);
    }


    /**
     * Read from file
     *
     * @param path
     * @param includeUnicode
     * @return
     * @throws IOException
     */
    public String read(String path, boolean includeUnicode) throws IOException {
        synchronized (this) {
            FileInputStream i = new FileInputStream(createFile(path));
            StringBuffer output = new StringBuffer();
            int data;
            while ((data = i.read()) > 0) {
                if(includeUnicode || data < 0x80) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    /**
     * Read from file without unicode
     *
     * @return
     * @throws IOException
     */
    public String readWithoutUnicode(String path) throws IOException {
        synchronized (this) {
            FileInputStream i = new FileInputStream(createFile(path));
            StringBuffer output = new StringBuffer();
            int data;
            while ((data = i.read()) > 0) if (data < 0x80) {
                output.append((char) data);
            }
            return output.toString();
        }
    }

    /**
     * Write to file
     *
     * @param input
     * @throws IOException
     */
    public void write(String input, String path) throws IOException {
        synchronized (this) {
            FileOutputStream o = new FileOutputStream(createFile(path));
            for (int i = 0; i < input.length(); i += 1) {
                o.write(input.charAt(i));
            }
        }
    }
}