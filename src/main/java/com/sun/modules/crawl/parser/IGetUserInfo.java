package com.sun.modules.crawl.parser;

import com.sun.modules.bean.domain.UserDO;
import com.sun.modules.bean.domain.VideoDO;

import java.io.IOException;
import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public interface IGetUserInfo {

    /**
     * 获取用户信息
     *
     * @param videoDOList
     * @return
     * @throws IOException
     */
    List<UserDO> getUserInfo(List<VideoDO> videoDOList) throws IOException;
}
