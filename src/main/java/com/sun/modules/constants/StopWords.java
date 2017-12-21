package com.sun.modules.constants;

import com.sun.modules.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StopWords {

    public static List<String> STOP_WORD = new ArrayList<String>();

    static{
        try {
            String readWords = FileUtil.readFileAllContents("./src/main/resources/dic/stopWord.dic");
            String[] words = readWords.trim().split(" ");
            STOP_WORD.addAll(new ArrayList<String>(Arrays.asList(words)));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
