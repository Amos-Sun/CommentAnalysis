package com.sun.modules.show.service;

import com.sun.modules.bean.po.VideoPO;
import com.sun.modules.bean.vo.VideoVO;

import java.util.List;

public interface IVideoService {

    String strTest();

    /**
     * 获取所有电影
     *
     * @return
     */
    List<VideoVO> getAllVideos();

    /**
     * 根据页码获取电影
     *
     * @param pageNum  当前是第几页
     * @param pageSize 一页有多少数据
     * @return
     */
    List<VideoVO> getVideosByPage(Integer pageNum, Integer pageSize);
}
