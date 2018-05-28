package com.sun.modules.show.service;

import com.sun.modules.bean.vo.VideoVO;

import java.util.List;

public interface IVideoService {

    List<VideoVO> getVideosByGoodPercent(Integer pageNum, Integer pageSize);

    List<VideoVO> getVideosBySexGood(String sex, Integer pageNum, Integer pageSize);

    int getTotalNum();

    VideoVO getByCid(String cid);
}
