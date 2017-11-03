package com.sun.moudles;

import com.sun.moudles.analysis.WordSegmentation;
import com.sun.moudles.util.FileUtil;

import java.io.IOException;
import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class WordFileTest {

    /**
     * 存所有停用词，去重
     */
    private Set<String> wordsSet = new HashSet<String>();

    /**
     * 把分词结果存到文件中
     *
     * @param filePath    未分词文件路径
     * @param newFileName 分词后，存储路径
     */
    private void writeClassWords(String filePath, String newFileName) throws Exception {
        File file = new File(filePath);
        File[] files = file.listFiles();

        WordSegmentation wordSegmentation = new WordSegmentation();
        String fileText;
        String segResult;
        int numbers = 0;
        List<String> wordList = new ArrayList<String>();
        for (File item : files) {
            if (numbers == 26) {
                break;
            }
            fileText = FileUtil.readFileAllContents(item.getPath());
            segResult = wordSegmentation.ikAnalyzer(fileText);
           // String[] words = segResult.split("\\|");

            //每一个文本存成一个向量
            wordList.add(segResult.trim());
            numbers++;
        }
        FileUtil.writeFileInBatch(wordList, newFileName);
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
            throw new IOException("打开文件失败  " + filePath);
        }
    }

    @Test
    public void setStopWordFile() {
        try {
            setStopWords("./data/stop2.txt");
            setStopWords("./data/strop3.txt");
            setStopWords("./data/stopWords.txt");

            FileUtil.writeFileInBatch(wordsSet, "./data/stopWord.dic");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 扩展词
     */
    private String extendWord = "邢佳栋 李学庆 高昊 潘粤明 戴军 薛之谦 贾宏声 于波 李连杰 王斑 蓝雨 刘恩佑 任泉 李光洁 姜文 黑龙 张殿菲 邓超 张杰 杨坤 沙溢 李茂 黄磊 于小伟 刘冠翔 秦俊杰 张琳 陈坤 黄觉 邵峰 陈旭 马天宇 杨子 邓安奇 赵鸿飞 马可 黄海波 黄志忠 李晨 后弦 王挺 何炅 朱亚文 胡军 许亚军 张涵予 贾乃亮 陆虎 印小天 于和伟 田亮 夏雨 李亚鹏 胡兵 王睿 保剑锋 于震 苏醒 胡夏 张丰毅 刘翔 李玉刚 林依轮 袁弘 朱雨辰 丁志诚 黄征 张子健 许嵩 向鼎 陆毅 乔振宇 闫肃 李健 王啸坤 胡歌 吉杰 吴俊余 韩寒 黄海冰 魏晨 郭敬明 何晟铭 巫迪文 谢苗 郑源 欢子 文章 陈翔 井柏然 左小祖咒 含笑 李咏 徐誉滕 段奕宏 李炜 罗中旭 张远 李立 释小龙 大左 君君 毛宁 樊凡 周一围 于荣光 汤潮 张晓晨 吴京 山野 陈龙 侯勇 张国强 玉米提 周觅 张丹峰 俞思远 姚明 冯绍峰 陈玉建 吴建飞 郑钧 胡彦斌 李智楠 钱枫 高曙光 谢和弦 陈道明 柳云龙 汪峰 陈楚生 陈思成 魏晨 马雪阳 袁成杰 崔健 杜淳 林申 刘洲成 黄晓明 刘烨 张翰 杨洋 宋晓波 解小东 窦唯 姜武 陈泽宇 彭坦 张一山 李易峰 严宽 东来东往 张国立 王志文 佟大为 柏栩栩 蒲巴甲 凌潇肃 李行亮 毛方圆 张嘉译 大张伟 师洋 李幼斌 张磊 朱梓骁 武艺 杨俊毅 耿乐 钱泳辰 撒贝宁 徐峥 谭杰希 黄晟晟 海鸣威 汪涵 王学兵 贾一平 孙红雷 袁文康 蔡国庆 吴秀波 王栎鑫 安琥 刘心 俞灏明 张超 于小彤 张峻宁 乔任梁 朴树 赵帆 张译 聂远 张敬轩 付辛博 黄明 杜海涛 李宇春 张靓颖 周笔畅 何洁 刘亦菲 张含韵 陈好 尚雯婕 汤唯 张筱雨 韩雪 孙菲菲 张嘉倪 霍思燕 陈紫函 朱雅琼 江一燕 厉娜 许飞 胡灵 郝菲尔 刘力扬 reborn 章子怡 谭维维 魏佳庆 张亚飞 李旭丹 孙艺心 巩贺 艾梦萌 闰妮 王蓉 汤加丽 汤芳 牛萌萌 范冰冰 赵薇 周迅 金莎 纪敏佳 黄雅莉 叶一茜 马苏 阿桑 董卿 金铭 徐行 姚笛 朱妍 夏颖 陈西贝 冯家妹 高娅媛 林爽 郑靖文 陶虹 徐静蕾 黄奕 董洁 巩俐 高圆圆 于娜 孟广美 Gameapple  美女奉奉 小龙女彤彤 张子萱  果子 丁贝莉 吸血芭比 公交MM 香香 段思思 二月丫头 刘羽琦 dodolook 拉拉公主 沈丽君 周璟馨 丁叮 谢雅雯 陈嘉琪 宋琳 郭慧敏 卢洁云 佘曼妮 黄景 马艳丽 蒋雯丽 宁静 许晴 张静初 瞿颖 张延 孙俪 闵春晓 蔡飞雨 邓莎 白冰 程媛媛 吴婷 殷叶子 朱伟珊 孙菂 赵梦恬 龚洁 许晚秋 杨舒婷 乔维怡 王海珍 易慧 谢雨欣 陈娟红 舒畅 李小璐 曹颖 李冰冰 王艳 沈星 阿朵 周洁 杨林 李霞 陈自瑶 李小冉 李湘 金巧巧 蒋勤勤 梅婷 刘涛 秦海璐 安又琪 杨钰莹 马伊俐 陈红 鲍蕾 牛莉 胡可 杨幂 龚蓓苾 田震 杨童舒 吕燕 王姬 苗圃 李欣汝 王小丫 秦岚 徐帆 刘蓓 彭心怡 邓婕 眉佳 李媛媛 刘晓庆 杨若兮 黄圣依 林熙 薛佳凝 斯琴格日乐 宋祖英 郝蕾 乐珈彤 冯婧 宋丹丹 盖丽丽 田海蓉 杨澜 沈冰 王宇婕 王希维 姜培琳 何晴 焦媛 白灵 胡静 陈冲 刘怡君 韦唯 龚雪 周彦宏 刘丹 傅艺伟 谢东娜 朱媛媛 黑鸭子 周璇 吕丽萍 杨欣 陈小艺 伍宇娟 苏瑾 李玲玉 张凯丽 潘虹 沈丹萍 岳红 赵静怡 宋晓英 吴卓羲 游鸿明 胡宇崴 张震岳 汤镇业 黄立行 苗侨伟 周星驰 温升豪 萧敬腾 窦智孔 陈汉典 郑伊健 陈国坤 张信哲 范逸臣 王绍伟 辰亦儒 张卫健 周汤豪 成龙 林志颖 苏有朋 温兆伦 吴建豪 黄家驹 卢广仲 林文龙 赵又廷 刘德华 周传雄 李治廷 周华健 钟镇涛 周渝民 陈柏霖 邱心志 陈百强 郑元畅 王杰 狄龙 郭富城 光良 黄浩然 彭于晏 马浚伟 蓝正龙 林佑威 杜德伟 费翔 许志安 黄义达 黄耀明 陈键锋 王喜 黄贯中 江华 贺一航 郑少秋 蔡康永 陈伟霆 黄宗泽 刘畊宏 梁家辉 林志炫 赵文卓 樊少皇 连凯 吴镇宇 哈狗帮 吴尊 张国荣 方大同 刘松仁 郑嘉颖 周柏豪 王祖蓝 古巨基 萧正楠 邹兆龙 李铭顺 吴奇隆 金城武 李圣杰 陈建州 余文乐 罗志祥 吴启华 李克勤 秦汉 单立文 汪东城 莫少聪 陈冠希 黄秋生 罗嘉良 欧弟 马国明 范植伟 阮经天 郑中基 张智霖 麦浚龙 林宥嘉 姜大卫 陈浩民 杰西达邦 刘青云 黄子华 丁子高 童安格 王力宏 卢广仲 阿杜 方中信 孙兴 陈奕迅 周定纬 林子祥 任达华 黄志玮 陈晓东 潘玮柏 黄日华 张学友 陈柏宇 言承旭 郭品超 周润发 陈楚河 周杰伦 何家劲 张栋梁 谢霆锋 古天乐 甄子丹 梁朝伟 房祖名 叶世荣 张孝全 侧田 立威廉 李灿森 谭咏麟 曹格 方文山 安志杰 黎明 陈司翰 朱孝天 王子 敖犬 黄维德 张家辉 赵文瑄 吕良伟 关智斌 张耀扬 何润东 钟汉良 马德钟 林俊杰 齐秦 高以翔 焦恩俊 张雨生 任贤齐 杜汶泽 孙协志 林峰 吕颂贤 马景涛 许绍洋 吴彦祖 阿信 贺军翔 张智尧 冯德伦 TAE 陈小春 明道 黄靖伦 元彪 麻吉弟弟 孙耀威 唐禹哲 刘德凯 信 杨佑宁 吴克群 霍建华 邱泽 吴青峰 欧阳震华 李小龙 李威 张宇 方力申 洪卓立 炎亚纶 庾澄庆 蔡依林 张韶涵 王心凌 徐若瑄 林志玲 王菲 S.H.E Twins 徐熙媛 桂纶镁 林依晨 陈乔恩 梁静茹 蔡诗芸 范玮琪 廖碧儿 张柏芝 李嘉欣 容祖儿 李玟 贾静雯 MaggieQ 林心如 朱茵 叶璇 唐宁 曾之乔 安以轩 杨丞琳  侯佩岑 同恩 陈松伶 文颂娴 梁凯蒂 林韦君 陈思璇 曹敏莉 乐基儿 郑雪儿 佘诗曼 郑秀文 萧蔷  温碧霞 刘嘉玲 刘玉玲 林熙蕾 李若彤 张曼玉 关之琳 陈慧琳 萧淑慎 蔡少芬 萧亚轩 田丽 杨采妮 李丽珍 琦琦 天心 任港秀 杨思敏 郭静纯 钟丽缇 孙燕姿 叶玉卿 翁红 邱淑贞 蔡淑臻 梁咏琪 季芹 舒淇 莫文蔚 戴佩妮 刘若英 杨千桦 范伟琪 徐熙娣 陈宝莲 吴辰君 张庭 林嘉欣 俞飞鸿 叶子楣 周海媚 伊能静 蜜雪薇琪  侯湘婷 Hebe 应采儿 许茹芸 吴佩慈 郑希怡 范文芳 李彩桦 蔡淳佳 本多RuRu 范晓萱 张惠妹 林忆莲 关心妍 卓依婷 杨恭如 陈文媛 吴小莉 梅艳芳 林青霞 赵雅芝 孟庭苇 吴倩莲 陈慧珊 许慧欣 黎姿 周慧敏 钟楚红 蔡琴 齐豫 邓丽君 林凤娇 陈玉莲 周冰倩 杨惠姗 金素梅 翁美玲 高胜美 甄妮 胡慧中 邝美云 俞小凡 吕秀菱 萧芳芳 刘雪华 潘迎紫 梁雁翎 汪明荃 苏芮 冯宝宝 利智 张艾嘉 叶倩文 陈淑桦 郑裕玲 潘越云 凤飞飞 喻可欣 刘恺威 窦智孔 胡宇崴 欧阳震华 钟汉良 吴克群 twins 郭采洁 陈庭妮";

    @Test
    public void setExtendDic() {

        String[] words = extendWord.split(" ");
        List<String> wordList = new ArrayList<String>(Arrays.asList(words));
        try {
            FileUtil.writeFileInBatch(wordList, "D:\\self\\CommentAnalysis\\src\\main\\resources\\dic\\my.dic");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}