package com.sun.moudles.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件存储操作
 *
 * @author SunGuiyong
 *         2017/10/23.
 */
public class FileHandler {

    /**
     * 写入文件
     *
     * @param content  要写入的文件内容
     * @param fileName 文件名字
     * @throws IOException
     */
    public static void writeFile(String content, String fileName) throws IOException {
        File file = new File(fileName);
        String msg = createFile(file);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(content);
            bw.flush();// 把缓存区内容压入文件
            bw.close();// 最后记得关闭文件
        } catch (IOException e) {
            throw new IOException(msg);
        }
    }

    /**
     * 创建文件
     *
     * @param fileName
     * @return
     */
    private static String createFile(File fileName) throws IOException{
        if (fileName.exists()) {
            return "文件已存在";
        } else {
            fileName.createNewFile();
            return "文件创建成功";
        }
    }
}
