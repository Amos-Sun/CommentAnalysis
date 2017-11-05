package com.sun.moudles.analysis;

import java.io.*;
import java.util.Iterator;

import com.sun.moudles.constants.StopWords;
import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Created by SunGuiyong on 2017/10/10.
 */
@Component
public class WordSegmentation {
    /**
     * smartcn 分词
     *
     * @param handleText
     * @throws IOException
     */
    public String smartcnPartWords(String handleText) throws IOException {
        StringBuilder sbContent = new StringBuilder();
        // 自定义停用词
        //String[] self_stop_words = {"的", "在", "了", "呢", "，", "0", "：", ",", "是"};
        CharArraySet cas = new CharArraySet(Version.LUCENE_48, 0, true);
        for (int i = 0; i < StopWords.STOP_WORD.size(); i++) {
            cas.add(StopWords.STOP_WORD.get(i));
        }

        // 加入系统默认停用词
        Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
        while (itor.hasNext()) {
            cas.add(itor.next());
        }

        // 中英文混合分词器(其他几个分词器对中文的分析都不行)
        SmartChineseAnalyzer sca = new SmartChineseAnalyzer(Version.LUCENE_48, cas);

        TokenStream ts = sca.tokenStream("field", handleText);
        CharTermAttribute ch = ts.addAttribute(CharTermAttribute.class);

        ts.reset();
        while (ts.incrementToken()) {
            sbContent.append(ch.toString() + "|");
            System.out.print(ch.toString() + "|");
        }
        System.out.println();
        ts.end();
        ts.close();
        return sbContent.toString();
    }

    /**
     * paoding分词
     *
     * @param indexStr
     * @throws IOException
     */
    public String paodingAnalysis(String indexStr) throws IOException {
        StringBuilder sbContent = new StringBuilder();
        Analyzer analyzer = new PaodingAnalyzer();
        StringReader reader = new StringReader(indexStr);
        TokenStream ts = analyzer.tokenStream(indexStr, reader);
        ts.reset();
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            sbContent.append(term + "|");
            System.out.print(term + "|");
        }
        System.out.println();
        return sbContent.toString();

        /*String dataDir = "data";
        String indexDir = "luceneindex";

        File[] files = new File(dataDir).listFiles();
        System.out.println(files.length);

        Analyzer analyzer = new PaodingAnalyzer();
        Directory dir = FSDirectory.open(new File(indexDir));
        IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_48, analyzer));

        for (int i = 0; i < files.length; i++) {
            StringBuffer strBuffer = new StringBuffer();
            String line = "";
            FileInputStream is = new FileInputStream(files[i].getCanonicalPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf8"));
            line = reader.readLine();
            while (line != null) {
                strBuffer.append(line);
                strBuffer.append("\n");
                line = reader.readLine();
            }

            Document doc = new Document();
            doc.add(new Field("fileName", files[i].getName(), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("contents", strBuffer.toString(), Field.Store.YES, Field.Index.ANALYZED));
            writer.addDocument(doc);
            reader.close();
            is.close();
        }

        writer.commit();
        writer.close();
        dir.close();
        System.out.println("ok");*/
    }

    public void readIndex() throws Exception {
        String indexDir = "luceneindex";
        Analyzer analyzer = new PaodingAnalyzer();
        String search_text = "六小龄童的眼睛和耳朵变成小和尚";
        StringReader reader = new StringReader(search_text);
        TokenStream ts = analyzer.tokenStream(search_text, reader);
        CharTermAttribute ta = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print(ta.toString() + " ");
        }
        ts.close();

        Directory dir = FSDirectory.open(new File(indexDir));
        DirectoryReader dr = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(dr);
        QueryParser parser = new QueryParser(Version.LUCENE_48, "contents",
                analyzer);
        Query query = parser.parse(search_text);
        //Term term=new Term("contents", search_text);
        //TermQuery query=new TermQuery(term);
        System.out.println("\n" + query.toString());

        TopDocs docs = searcher.search(query, 1000);
        ScoreDoc[] hits = docs.scoreDocs;
        System.out.println(hits.length);
        for (int i = 0; i < hits.length; i++) {
            Document doc = searcher.doc(hits[i].doc);
            System.out.print(doc.get("fileName") + "--:\n");
            System.out.println(doc.get("contents") + "\n");
        }

        // searcher.close();
        dir.close();
    }

    /**
     * IK分词
     *
     * @param news
     * @throws Exception
     */
    public String ikAnalyzer(String news) throws Exception {
        StringBuilder sbContent = new StringBuilder();
      /* Analyzer analyzer = new IKAnalyzer(false);//不用只能分词
        StringReader reader = new StringReader(news);
        TokenStream ts = analyzer.tokenStream(news, reader);
        ts.reset();
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print(term.toString() + "|");
        }
        analyzer.close();
        reader.close();
        */
        StringReader re = new StringReader(news);
        IKSegmenter ik = new IKSegmenter(re, true);
        Lexeme lex = null;
        while ((lex = ik.next()) != null) {
            if (StopWords.STOP_WORD.contains(lex.getLexemeText())) {
                continue;
            }
            sbContent.append(lex.getLexemeText() + "|");
        }
        return sbContent.toString();
    }
}
