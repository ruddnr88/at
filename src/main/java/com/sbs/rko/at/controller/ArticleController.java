package com.sbs.rko.at.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.rko.at.dto.Article;
import com.sbs.rko.at.dto.ArticleReply;
import com.sbs.rko.at.dto.Member;
import com.sbs.rko.at.dto.ResultData;
import com.sbs.rko.at.service.ArticleService;
import com.sbs.rko.at.util.Util;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	// 게시물리스트보기
	@RequestMapping("/usr/article/list")
	public String showList(Model model, HttpServletRequest request) {
		int page = 1;
		String searchKeywordType = "";
		String searchKeyword = "";

		int itemsInAPage = 10;
		int totalCount = articleService.getTotalCount(searchKeywordType, searchKeyword);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);

		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);

		List<Article> articles = articleService.getForPrintArticles(page, itemsInAPage);
		model.addAttribute("articles", articles);

		return "article/list";
	}

	// 게시물상세보기(디테일)
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, @RequestParam Map<String, Object> param) {
		int id = Integer.parseInt((String) param.get("id"));

		Article article = articleService.getForPrintArticleById(id);
		model.addAttribute("article", article);
		
		
		return "article/detail";
	}

	// 글쓰기이동
	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "article/write";
	}

	// 글쓰기
	@RequestMapping("/usr/article/doWrite")
	public String DoWrite(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {

		int newArticleId = articleService.write(param);
		
		String redirectUrl = (String) param.get("redirectUrl");
		redirectUrl = redirectUrl.replace("#id", newArticleId + "");

		return "redirect:" + redirectUrl;
	}

	// 댓글작성
//	@RequestMapping("/usr/article/doWriteReply")
//	public String doWriteReply(Model model, @RequestParam Map<String, Object> param) {
//
//		int articleReplyId = articleService.writeReply(param);
//
//		String redirectUrl = (String) param.get("redirectUrl");
//		redirectUrl = redirectUrl.replace("#id", articleReplyId + "");
//
//		return "redirect:" + redirectUrl;
//	}

	// 댓글작성Ajax
	@RequestMapping("/usr/article/doWriteReplyAjax")
	@ResponseBody
	public ResultData doWriteReplyAjax(@RequestParam Map<String, Object> param, HttpServletRequest request) {
		Map<String, Object> rsDataBody = new HashMap<>();
		param.put("memberId", request.getAttribute("loginedMemberId"));
		int newArticleReplyId = articleService.writeReply(param);
		rsDataBody.put("articleReplyId", newArticleReplyId);

		return new ResultData("S-1", String.format("%d번 댓글이 생성되었습니다.", newArticleReplyId), rsDataBody);
	}

	// 댓글삭제
	@RequestMapping("/usr/article/doDeleteReplyAjax")
	@ResponseBody
	public ResultData doDeleteReplyAjax(int id) {
		articleService.deleteReply(id);

		return new ResultData("S-1", String.format("%d번 댓글을 삭제하였습니다.", id));
	}

//	// 댓글수정이동하기
//	@RequestMapping("/usr/article/modifyReply")
//	public String showModifyReply(Model model, @RequestParam Map<String, Object> param, int id, HttpServletRequest request) {
//
//		ArticleReply articleReply = articleService.getForPrintArticleReply(id);
//		model.addAttribute("articleReply", articleReply);
//
//		return "article/modifyReply";
//	}

	// 댓글수정Ajax
	@RequestMapping("/usr/article/doModifyReplyAjax")
	@ResponseBody
	public ResultData doModifyReplyAjax(@RequestParam Map<String, Object> param, int id, HttpServletRequest req) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		ArticleReply articleReply = articleService.getForPrintArticleReplyById(id);
		
		if ( articleService.actorCanModify(loginedMember, articleReply) == false ) {
			return new ResultData("F-1", String.format("%d번 댓글을 수정할 권한이 없습니다.", id));
		}
		
		Map<String, Object> modfiyReplyParam = Util.getNewMapOf(param, "id", "body");
		ResultData rd = articleService.modifyReply(modfiyReplyParam);
		
		return rd;
	}
	//댓글리스트Ajax
	@RequestMapping("/usr/article/getForPrintArticleReplies")
	@ResponseBody
	public ResultData getForPrintArticleReplies(@RequestParam Map<String, Object> param,HttpServletRequest req) {
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		Map<String, Object> rsDataBody = new HashMap<>();
		
		param.put("actor", loginedMember);

		List<ArticleReply> articleReplies = articleService.getForPrintArticleReplies(param);
		rsDataBody.put("articleReplies", articleReplies);

		return new ResultData("S-1", String.format("%d개의 댓글을 불러왔습니다.", articleReplies.size()), rsDataBody);
	}

	// 게시물삭제
	@RequestMapping("/usr/article/delete")
	@ResponseBody
	public String showDelete(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {
		int id = Integer.parseInt((String) param.get("id"));
		articleService.delete(id);
		return "html:<script> alert('" + id + "번 게시물이 삭제되었습니다.'); location.replace('list'); </script>";
	}

	// 게시물 수정이동
	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, @RequestParam Map<String, Object> param, int id, HttpServletRequest request) {
		Article article = articleService.getForPrintArticleById(id);
		model.addAttribute("article", article);

		return "article/modify";
	}

	// 게시물수정
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String DoModify(@RequestParam Map<String, Object> param, int id, HttpServletRequest request) {

		articleService.modify(param);
		return "html:<script> alert('" + id + "번 게시물이 수정되었습니다.'); location.replace('./detail?id=" + id
				+ "'); </script>";
	}

}
