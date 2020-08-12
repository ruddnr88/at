package com.sbs.rko.at.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.rko.at.dto.Reply;

@Mapper
public interface ReplyDao {
	void writeReply(Map<String, Object> param);

	List<Reply> getForPrintReplies(Map<String, Object> param);

	void deleteReply(@Param("id") int id);

	Reply getForPrintReply(@Param("id") int id);

	void modifyReply(Map<String, Object> param);

	Reply getForPrintReplyById(@Param("id") int id);

}
