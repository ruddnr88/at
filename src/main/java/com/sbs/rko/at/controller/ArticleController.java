package com.sbs.rko.at.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.rko.at.dto.Article;
import com.sbs.rko.at.dto.ArticleReply;
import com.sbs.rko.at.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	// 게시물리스트보기
	@RequestMapping("/article/list")
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
	@RequestMapping("/article/detail")
	public String showDetail(Model model, @RequestParam Map<String, Object> param,int id, HttpServletRequest request) {

		Article article = articleService.getForPrintArticleById(id);
		model.addAttribute("article", article);
		
		List<ArticleReply> articleReplies = articleService.getForPrintArticleReplies(article.getId());
		model.addAttribute("articleReplies", articleReplies);
		return "article/detail";
	}

	// 글쓰기이동
	@RequestMapping("/article/write")
	public String showWrite() {
		return "article/write";
	}

	// 글쓰기
	@RequestMapping("/article/doWrite")
	public String DoWrite(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {

		int newArticleId = articleService.write(param);
		String redirectUrl = (String) param.get("redirectUrl");
		redirectUrl = redirectUrl.replace("#id", newArticleId + "");

		return "redirect:" + redirectUrl;
	}

	// 댓글작성
//	@RequestMapping("/article/doWriteReply")
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
	@RequestMapping("/article/doWriteReplyAjax")
	@ResponseBody
	public Map<String, Object> doWriteReplyAjax(@RequestParam Map<String, Object> param,HttpServletRequest request) {
		 Map<String, Object> rs = articleService.writeReply(param);
		return rs;
	}

	// 댓글삭제
	@RequestMapping("/article/doDeleteReply")
	@ResponseBody
	public String doDeleteReply(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {
		int id = Integer.parseInt((String) param.get("id"));
		articleService.doArticleReplydelete(id);

		return "html:<script> alert('" + id + "번 댓글이 삭제되었습니다.');location.href = document.referrer; </script>";
	}

	// 댓글수정이동하기
	@RequestMapping("/article/modifyReply")
	public String showModifyReply(Model model, @RequestParam Map<String, Object> param, int id, HttpServletRequest request) {

		ArticleReply articleReply = articleService.getForPrintArticleReply(id);
		model.addAttribute("articleReply", articleReply);

		return "article/modifyReply";
	}

	// 댓글수정
	@RequestMapping("/article/doModifyReply")
	public String doModifyReply(@RequestParam Map<String, Object> param, int id, HttpServletRequest request) {
		articleService.modifyReply(param);

		String redirectUrl = (String) param.get("redirectUrl");
		redirectUrl = redirectUrl.replace("#id", id + "");

		return "redirect:" + redirectUrl;
	}

	// 게시물삭제
	@RequestMapping("/article/delete")
	public String showDelete(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {
		int id = Integer.parseInt((String) param.get("id"));
		articleService.delete(id);
		return "html:<script> alert('" + id + "번 게시물이 삭제되었습니다.'); location.replace('list'); </script>";
	}

	// 게시물 수정이동
	@RequestMapping("/article/modify")
	public String showModify(Model model, @RequestParam Map<String, Object> param, int id, HttpServletRequest request) {
		Article article = articleService.getForPrintArticleById(id);
		model.addAttribute("article", article);

		return "article/modify";
	}

	// 게시물수정
	@RequestMapping("/article/doModify")
	@ResponseBody
	public String DoModify(@RequestParam Map<String, Object> param, int id, HttpServletRequest request) {

		articleService.modify(param);
		return "html:<script> alert('" + id + "번 게시물이 수정되었습니다.'); location.replace('./detail?id=" + id
				+ "'); </script>";
	}

}
