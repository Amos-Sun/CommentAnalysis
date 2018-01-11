package com.sun.modules.crawl.parser;

import com.sun.modules.bean.po.UserPO;
import com.sun.modules.bean.po.VideoPO;

import java.io.IOException;
import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public interface ISaveUserAndRelationDetail {

    /**
     * 获取用户信息
     *
     * @param videoDOList
     * @return
     * @throws IOException
     */
    List<UserPO> saveUserAndRelation(List<VideoPO> videoDOList) throws IOException;
}
