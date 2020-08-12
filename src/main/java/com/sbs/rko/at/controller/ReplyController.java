package com.sbs.rko.at.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.sbs.rko.at.dto.Member;
import com.sbs.rko.at.dto.Reply;
import com.sbs.rko.at.dto.ResultData;
import com.sbs.rko.at.service.ReplyService;
import com.sbs.rko.at.util.Util;

@Controller
public class ReplyController {
	@Autowired
	private ReplyService replyService;

	// 댓글작성Ajax
		@RequestMapping("/usr/article/doWriteReplyAjax")
		@ResponseBody
		public ResultData doWriteReplyAjax(@RequestParam Map<String, Object> param, HttpServletRequest request) {
			Map<String, Object> rsDataBody = new HashMap<>();
			param.put("memberId", request.getAttribute("loginedMemberId"));
			param.put("relTypeCode", "article");
			Util.changeMapKey(param,"articleId", "relId");
			int newReplyId = replyService.writeReply(param);
			rsDataBody.put("replyId", newReplyId);

			return new ResultData("S-1", String.format("%d번 댓글이 생성되었습니다.", newReplyId), rsDataBody);
		}

		// 댓글삭제
		@RequestMapping("/usr/article/doDeleteReplyAjax")
		@ResponseBody
		public ResultData doDeleteReplyAjax(int id, HttpServletRequest req) {
			Member loginedMember = (Member) req.getAttribute("loginedMember");
			Reply reply = replyService.getForPrintReplyById(id);

			if (replyService.actorCanDelete(loginedMember, reply) == false) {
				return new ResultData("F-1",String.format("%d번 댓글을 삭제할 권한이 없습니다.", id));

			}
			replyService.deleteReply(id);

			return new ResultData("S-1", String.format("%d번 댓글을 삭제하였습니다.", id));
		}

		// 댓글수정Ajax
		@RequestMapping("/usr/article/doModifyReplyAjax")
		@ResponseBody
		public ResultData doModifyReplyAjax(@RequestParam Map<String, Object> param, int id, HttpServletRequest req) {
			Member loginedMember = (Member) req.getAttribute("loginedMember");
			Reply reply = replyService.getForPrintReplyById(id);

			if (replyService.actorCanModify(loginedMember, reply) == false) {
				return new ResultData("F-1", String.format("%d번 댓글을 수정할 권한이 없습니다.", id));
			}

			Map<String, Object> modfiyReplyParam = Util.getNewMapOf(param, "id", "body");
			ResultData rd = replyService.modifyReply(modfiyReplyParam);

			return rd;
		}

		// 댓글리스트Ajax
		@RequestMapping("/usr/article/getForPrintReplies")
		@ResponseBody
		public ResultData getForPrintReplies(@RequestParam Map<String, Object> param, HttpServletRequest req) {
			Member loginedMember = (Member) req.getAttribute("loginedMember");
			Map<String, Object> rsDataBody = new HashMap<>();
			
			param.put("relTypeCode", "article");
			Util.changeMapKey(param, "articleId", "relId");

			param.put("actor", loginedMember);

			List<Reply> replies = replyService.getForPrintReplies(param);
			rsDataBody.put("replies", replies);

			return new ResultData("S-1", String.format("%d개의 댓글을 불러왔습니다.", replies.size()), rsDataBody);
		}
		
		
		// 액터가 해당 댓글을 수정할 수 있는지 알려준다.
		public boolean actorCanModify(Member actor, Reply reply) {
			return actor != null && actor.getId() == reply.getMemberId() ? true : false;
		}

		// 액터가 해당 댓글을 삭제할 수 있는지 알려준다.
		public boolean actorCanDelete(Member actor, Reply reply) {
			return actorCanModify(actor, reply);
		}


}
