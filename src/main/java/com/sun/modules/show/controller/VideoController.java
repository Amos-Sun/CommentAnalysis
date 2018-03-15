package com.sun.modules.show.controller;

import com.sun.modules.bean.vo.VideoVO;
import com.sun.modules.common.response.MsgResponse;
import com.sun.modules.common.response.OtherInfo;
import com.sun.modules.show.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by SunGuiyong
 * on 2018/1/26.
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @RequestMapping(value = "/get-all", method = {RequestMethod.GET})
    @ResponseBody
    public MsgResponse getAllVideos(Integer pageNum, Integer pageSize) {
        if (pageNum <= 0) {
            return MsgResponse.paragramError("当前页码小于0");
        }
        List<VideoVO> res = videoService.getVideosByGoodPercent(pageNum, pageSize);
        int totalRecord = videoService.getTotalNum();
        return MsgResponse.success(res, new OtherInfo(totalRecord));
    }

    @RequestMapping(value = "/get-man", method = {RequestMethod.GET})
    @ResponseBody
    public MsgResponse getByMan(Integer pageNum, Integer pageSize) {
        if (pageNum <= 0) {
            return MsgResponse.paragramError("当前页码小于0");
        }
        List<VideoVO> res = videoService.getVideosByManGood(pageNum, pageSize);
        int totalRecord = videoService.getTotalNum();
        return MsgResponse.success(res, new OtherInfo(totalRecord));
    }

    @RequestMapping(value = "/get-woman", method = {RequestMethod.GET})
    @ResponseBody
    public MsgResponse getByWoman(Integer pageNum, Integer pageSize) {
        if (pageNum <= 0) {
            return MsgResponse.paragramError("当前页码小于0");
        }
        List<VideoVO> res = videoService.getVideosByWomanGood(pageNum, pageSize);
        int totalRecord = videoService.getTotalNum();
        return MsgResponse.success(res, new OtherInfo(totalRecord));
    }
}
