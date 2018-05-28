package com.sun.modules.show.service.impl;

import com.sun.modules.bean.dao.IVideoDAO;
import com.sun.modules.bean.vo.VideoVO;
import com.sun.modules.bean.po.VideoPO;
import com.sun.modules.show.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private IVideoDAO videoDAO;

    @Override
    public List<VideoVO> getVideosByGoodPercent(Integer pageNum, Integer pageSize) {
        int num = (pageNum - 1) * pageSize;
        List<VideoPO> poList = videoDAO.getOrderByGoodPercent(num, pageSize);
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }
        return toVOList(poList);
    }

    @Override
    public List<VideoVO> getVideosBySexGood(String sex, Integer pageNum, Integer pageSize) {
        int num = (pageNum - 1) * pageSize;
        List<VideoPO> poList;
        if ("男".equals(sex)) {
            poList = videoDAO.getOrderByManGood(num, pageSize);
        } else {
            poList = videoDAO.getOrderByWomanGood(num, pageSize);
        }
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }
        return toVOList(poList);
    }

    @Override
    public int getTotalNum() {
        return videoDAO.getTotalRecord();
    }

    @Override
    public VideoVO getByCid(String cid) {
        VideoPO video = videoDAO.getByCid(cid);
        if (video == null) {
            return null;
        }
        return new VideoVO(video, cid);
    }

    private List<VideoVO> toVOList(List<VideoPO> poList) {
        List<VideoVO> list = new ArrayList<>();
        for (VideoPO item : poList) {
            VideoVO videoVO = new VideoVO(item);
            list.add(videoVO);
        }
        return list;
    }
}
