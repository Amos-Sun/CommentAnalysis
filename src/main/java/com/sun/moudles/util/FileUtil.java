package com.sun.moudles.util;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * 文件存储操作
 *
 * @author SunGuiyong 2017/10/23.
 */
public class FileUtil {

    /**
     * 不进行实例化
     */
    private FileUtil() {
        throw new Error("can't get this class's instance");
    }

    /**
     * 写入文件
     *
     * @param content  要写入的文件内容
     * @param fileName 文件名字
     */
    public static void writeFile(String content, String fileName) throws IOException {
        File file = new File(fileName);
        String msg = "";
        try {
            msg = createFile(file);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(content + "\r\n");
            bw.flush();// 把缓存区内容压入文件
            bw.close();// 最后记得关闭文件
        } catch (IOException e) {
            throw new IOException(msg);
        }
    }

    /**
     * 向文件中批量写去数据
     *
     * @param collection 传入的集合
     * @param filePath   文件名
     */
    public static void writeFileInBatch(Collection<String> collection, String filePath) throws IOException {
        String msg = "";
        BufferedWriter bw = null;
        try {
            File file = new File(filePath);
            msg = createFile(file);
            bw = new BufferedWriter(new FileWriter(file, false));
            Iterator<String> it = collection.iterator();
            String value;
            while (it.hasNext()) {
                value = it.next();
                if("".equals(value)){
                    continue;
                }
                bw.write(value + "\r\n");
                bw.flush();// 把缓存区内容压入文件
            }
        } catch (IOException e) {
            throw new IOException(msg);
        } finally {
            bw.close();// 最后记得关闭文件
        }


    }

    /**
     * 读取文件内容,按行读
     *
     * @param filePath 文件名
     */
    public static String readFileAllContents(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(" " + s);
        }
        return sb.toString();
    }

    /**
     * 创建文件
     */
    private static String createFile(File fileName) throws IOException {
        if (fileName.exists()) {
            return "文件已存在";
        } else {
            fileName.createNewFile();
            return "文件创建成功";
        }
    }
}
