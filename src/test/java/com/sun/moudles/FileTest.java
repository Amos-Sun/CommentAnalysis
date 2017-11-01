package com.sun.moudles;

import com.sun.moudles.analysis.WordSegmentation;
import com.sun.moudles.constants.StopWords;
import com.sun.moudles.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class FileTest {

    public void writeClassWords(String filePath, String newFileName) throws Exception {
        File file = new File(filePath);
        File[] files = file.listFiles();

        WordSegmentation wordSegmentation = new WordSegmentation();
        String fileText;
        String segResult;
        int numbers = 0;
        for (File item : files) {
            if (numbers == 26) {
                break;
            }
            fileText = FileUtil.readFileAllContents(item.getPath());
            segResult = wordSegmentation.ikAnalyzer(fileText);
            String[] words = segResult.split("\\|");
            for (String word : words) {
                FileUtil.writeFile(word, newFileName);
            }
            numbers++;
        }
    }

    @Test
    public void setClassWords() throws Exception {
        writeClassWords("./data/positive", "./data/positiveResult.txt");
        writeClassWords("./data/negative", "./data/negativeResult.txt");
        writeClassWords("./data/neuter", "./data/neuterResult.txt");
    }

    @Test
    public void readResultText() {
        try {
            String res = FileUtil.readFileAllContents("./data/positiveResult.txt");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readText() {
        try {
            String res = FileUtil.readFileAllContents("./data/positive/1.txt");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
