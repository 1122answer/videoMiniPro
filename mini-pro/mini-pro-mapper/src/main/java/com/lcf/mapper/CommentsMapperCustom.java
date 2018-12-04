package com.lcf.mapper;

import com.lcf.pojo.Comments;
import com.lcf.pojo.vo.CommentsVO;
import com.lcf.utils.MyMapper;

import java.util.List;

public interface CommentsMapperCustom extends MyMapper<Comments> {
	
	public List<CommentsVO> queryComments(String videoId);
}