package com.sbs.rko.at.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.rko.at.dao.ReplyDao;
import com.sbs.rko.at.dto.Member;
import com.sbs.rko.at.dto.Reply;
import com.sbs.rko.at.dto.ResultData;
import com.sbs.rko.at.util.Util;

@Service
public class ReplyService {
	@Autowired
	private ReplyDao replyDao;

	public int writeReply(Map<String, Object> param) {
		replyDao.writeReply(param);
		return Util.getAsInt(param.get("id"));
	}

	public List<Reply> getForPrintReplies(@RequestParam Map<String, Object> param) {
		List<Reply> replies = replyDao.getForPrintReplies(param);
		Member actor = (Member) param.get("actor");

		for (Reply reply : replies) {
			//출력용 부가 데이터 추가
			updateForPrintInfo(actor, reply);
		}

		return replies;
	}

	private void updateForPrintInfo(Member actor, Reply reply) {
		reply.getExtra().put("actorCanDelete", actorCanDelete(actor, reply));
		reply.getExtra().put("actorCanModify", actorCanModify(actor, reply));
	}

	// 액터가 해당 댓글을 수정할 수 있는지 알려준다.
	public boolean actorCanModify(Member actor, Reply reply) {
		return actor != null && actor.getId() == reply.getMemberId() ? true : false;
	}

	// 액터가 해당 댓글을 삭제할 수 있는지 알려준다.
	public boolean actorCanDelete(Member actor, Reply reply) {
			return actorCanModify(actor, reply);
	}

	public void deleteReply(int id) {
		replyDao.deleteReply(id);
	}

	public Reply getForPrintReply(int id) {
		Reply reply = replyDao.getForPrintReply(id);
		return reply;
	}

	public ResultData modifyReply(Map<String, Object> param) {
		replyDao.modifyReply(param);
		return new ResultData("S-1", String.format("%d번 댓글을 수정하였습니다.", Util.getAsInt(param.get("id"))), param);
	}

	public Reply getForPrintReplyById(int id) {
		return replyDao.getForPrintReplyById(id);
	}

}
