package com.sun.modules;

import com.sun.modules.analysis.WordSegmentation;
import com.sun.modules.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by SunGuiyong on 2017/10/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class SegmentationTest {

    /*@Resource
    private Analysis analysis;*/
    private static WordSegmentation wordSegmentation;

    static {
        AbstractApplicationContext ctx
                = new ClassPathXmlApplicationContext(new String[]{"spring-mybatis.xml"});
        wordSegmentation = (WordSegmentation) ctx.getBean("wordSegmentation");
    }


    private String text = "lucene分析器使用分词器和过滤器构成一个“管道”，" +
            "文本在流经这个管道后成为可以进入索引的最小单位，因此，" +
            "一个标准的分析器有两个部分组成，一个是分词器tokenizer," +
            "它用于将文本按照规则切分为一个个可以进入索引的最小单位。" +
            "另外一个是TokenFilter，它主要作用是对切出来的词进行进一步的处理" +
            "（如去掉敏感词、英文大小写转换、单复数处理）等。" +
            "lucene中的Tokenstram方法首先创建一个tokenizer对象处理Reader对象中的流式文本，" +
            "然后利用TokenFilter对输出流进行过滤处理";

    private String valuateText = "我家是准备买金毛，我才看这个电影的，因为我也很喜欢柯基。" +
            "我很喜欢狗，我家有只狮子狗，06年买成8块钱，卖它的是个老者，" +
            "我老妈说本来是一对的，要20块钱，等着卖了狗拿10块钱交电费。" +
            "我老妈那时手里只有八块钱，看着可爱采取看看的，后来就买了。" +
            "我老妈说卖的人说它（狮子狗）叫豆豆，我们以后叫它豆豆就好。" +
            "后来豆豆来我家，一个月都没有叫过，也不怎么吃饭，我们都以为它是哑巴。" +
            "因为那时老者都哭了，豆豆也哭了。以前和老者的孙子到处去放牛，" +
            "离开了肯定舍不得，为了交电费，特意给豆豆洗了澡，为了能卖出去，" +
            "豆豆肯定没有想到那是老者给它最后一次洗澡。豆豆来我家时我读初二，" +
            "现在我都工作快两年了；豆豆在的时候我奶奶很喜欢它，炖猪脚都要留一碗给豆豆，" +
            "吃鸡蛋糕都要给它一块。所以我家豆豆基本上什么都吃，" +
            "瓜子，花生，凉薯，西瓜，面条，馒头，雪糕，花生酥，饺子，梨子。。。。。。" +
            "在我家11年了，很多回忆。现在工作了，自己给它买了点狗狗专用火腿肠给它吃，" +
            "它现在都老了，走路都很费力。在我家，它也没有吃什么狗粮，人吃啥，它吃啥。" +
            "白菜，南瓜都吃，最喜欢鸡蛋汤泡饭。又乖，给家里带来很多欢乐。" +
            "准备买个金毛给豆豆作伴，我妈都不准，怕豆豆被金毛欺负，豆豆小气很。" +
            "但是它我家一只它带大的猫咪感情好，太奇怪了。现在我家一家子都很喜欢豆豆，" +
            "豆豆都不感觉它是狗，你在它面前叫狗，它没有反应，除非叫豆豆。" +
            "超级爱吃，只要塑料袋一响，马上就出现在你面前。现在，周围的小孩都知道我家豆豆，" +
            "我老爸也常说要是豆豆是小孩，今年都读初中了，这就是感情吧。人一辈子会有很多朋友，" +
            "一只狗一辈子只有一个主人。\n" +
            "在电影里的王子我看到三个事，第一，是老师不好当，社会很势力。" +
            "从那个佳琪的狗咬那个女生就知道了，虽说钱不重要，没钱的孩子在学校真的有可能会受气，" +
            "容易走极端。有钱人的孩子品德不好的，老师说了不听真的很无奈。我就见过学生家长有权有势的，" +
            "孩子犯错了，学校要开除，家长就不给开除，老师就说了学生在学校的问题，结果学生当着学校领导，" +
            "办公室老师的面给了老师一巴掌，外面还有学生围观。真的，老师也只能忍着。" +
            "现在虽然社会有老师的一些不好的事，但是不能以偏概全。当官的管50人的，" +
            "至少是县长了，创业的管50人的，都是规模不小的了。我觉得不要说老师都是为了钱，" +
            "哪个出门在外工作不是为了钱。做家长管自己的孩子都累，何况老师要管50个。其实哪一行都不容易。\n" +
            "第二，就是人性吧。王子看到人们乱丢垃圾会不平，人们的懒惰，人情的冷漠。还有就佳琪的守信，" +
            "说了后面给钱，就真的记得。这个电影很多方面，都在告诉我们，社会很无奈，也很勾心斗角。" +
            "你不可以改变别人，但是你可以努力改变自己。他们都是生活在社会底层的人，但是自立自强，" +
            "值得尊敬。以前的百万富翁，失败醒悟后，努力也会东山再起，所以做人要乐观积极。\n" +
            "第三，认真对待自己的宠物。他们很单纯，因为再聪明的狗狗也只有四到五岁孩子的智商。" +
            "要么不养，要么养一辈子。宠物希望我们陪它一辈子，而不是一阵子。我们一辈子有很多难忘的人，" +
            "很多要好的朋友。而宠物却只记得它的主人。所以电影里，王子后面来救佳琪，好感动，差点看哭了。\n" +
            "电影拍的很多环节不尽人意，但是目的是好的，所以我认为重点不在那里。总比拿钱翻拍，" +
            "或者拍一些没有意义的电影好多了。不知道，你们有没有养过宠物？给大家看看我家豆豆。";

    private String vaText = "看着特别心疼，让我哭了好久，从小都很喜欢狗狗，可是现在越来越大了，" +
            "在外面打拼根本养不了狗狗，但是就是特别特别想养，无奈真的没有办法";

    private String commentText = "大陆的导演你们看看。这特么才叫电影，郑华健";
    private String adjText = "高兴的，快乐的,这个好看，刘亦菲,人民大会堂，这个电影不错，好看，很好，非常好";

    @Test
    public void smartcnTest() {

        try {
            wordSegmentation.smartcnPartWords(commentText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ikTest() {
        try {
            System.out.println(adjText);
            System.out.println(wordSegmentation.ikAnalyzer(adjText));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void paodingTest() {
        try {
            /*wordSegmentation.paodingAnalysis(adjText);
            wordSegmentation.readIndex();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allTest(){
        try {
            StringBuilder text = new StringBuilder("===========smartcn============\r\n");
            System.out.println("===========smartcn============");
            text.append(wordSegmentation.smartcnPartWords(vaText)+"\r\n");

            System.out.println("===========paoding============");
            text.append("===========paoding============\r\n");
//            text.append(wordSegmentation.paodingAnalysis(vaText)+"\r\n");

            text.append("===========ikAnalyzer============\r\n");
            System.out.println("===========ikAnalyzer============\r\n");
            text.append(wordSegmentation.ikAnalyzer(vaText)+"\r\n");

            FileUtil.writeFile(text.toString(), "data//result.txt");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
