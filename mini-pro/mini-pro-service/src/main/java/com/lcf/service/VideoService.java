package com.lcf.service;

import com.lcf.pojo.Bgm;
import com.lcf.pojo.Comments;
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

    public PagedResult getAllVideos(Videos video,Integer isSaveRecord, Integer page,Integer pageSize);


    public List<String> getHotwords();
    public void saveComment(Comments comment);
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize);
    //点赞视频
    public void saveLike(String userId , String videoId);
    //取消点赞
    public void deleteLike(String userId , String videoId);

}
