package com.lcf.service;

import com.lcf.pojo.Bgm;
import com.lcf.pojo.Videos;
import com.lcf.utils.PagedResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VideoService {
    /**
     * @Description: 保存视频
     */
    public void saveVideo(Videos video);

    public PagedResult getAllVideos(Integer page,Integer pageSize);
}
