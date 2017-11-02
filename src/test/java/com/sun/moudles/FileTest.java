package com.sun.moudles;

import com.sun.moudles.analysis.WordSegmentation;
import com.sun.moudles.util.FileUtil;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class FileTest {

  /**
   * 存所有停用词，去重
   */
  private Set<String> wordsSet = new HashSet<String>();

  /**
   * 把分词结果存到文件中
   *
   * @param filePath 未分词文件路径
   * @param newFileName 分词后，存储路径
   */
  private void writeClassWords(String filePath, String newFileName) throws Exception {
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
      System.out.println(res.trim());
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

  /**
   * 整合停用词，形成一个新的无重复的停用词文件
   *
   * @param filePath 文件路径
   */
  private void setStopWords(String filePath) throws IOException {
    try {
      String content = FileUtil.readFileAllContents(filePath);
      String[] words = content.trim().split(" ");
      for (String word : words) {
        wordsSet.add(word);
      }
    } catch (IOException e) {
      throw new IOException("打开文件失败  "+filePath);
    }
  }

  @Test
  public void setStopWordFile() {
    try {
      setStopWords("./data/stop2.txt");
      setStopWords("./data/strop3.txt");
      setStopWords("./data/stopWords.txt");

      FileUtil.writeFileInBatch(wordsSet, "./data/stopWordNew.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
