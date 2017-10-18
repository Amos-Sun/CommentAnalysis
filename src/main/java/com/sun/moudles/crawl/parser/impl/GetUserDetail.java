package com.sun.moudles.crawl.parser.impl;

import com.sun.moudles.crawl.domain.UserDO;
import com.sun.moudles.crawl.domain.VideoDO;
import com.sun.moudles.crawl.parser.IGetUserInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public class GetUserDetail implements IGetUserInfo {

    public List<UserDO> getUserInfo(List<VideoDO> videoDOList) throws IOException {

        String infoUrl = "";
        for(VideoDO item : videoDOList){
            infoUrl = item.getVideoUrl();
        }
        return null;
    }
}
