package com.sun.modules.crawl.parser;

import com.sun.modules.bean.po.VideoPO;

import java.io.IOException;
import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public interface ISaveVideoDetail {

    /**
     * 获取电影信息
     *
     * @return
     * @throws IOException
     */
    List<VideoPO> saveVideoInfo() throws IOException;
}
