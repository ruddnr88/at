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
import com.sbs.rko.at.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	// 게시물리스트보기
	@RequestMapping("/usr/article/list")
	public String showList(Model model, String boardCode, String searchKeyword, String searchType,
			@RequestParam(value = "page", defaultValue = "1") int page, HttpServletRequest request) {

		if (searchType != null) {
			searchType = searchType.trim();
		}

		if (searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}
		
		Map<String, Object> getForPrintArticlesByParam = new HashMap();
		getForPrintArticlesByParam.put("searchKeyword", searchKeyword);
		getForPrintArticlesByParam.put("searchType", searchType);
		
		int itemsInAPage = 10;
		int limitCount = itemsInAPage;
		int limitFrom = (page - 1) * itemsInAPage;
		getForPrintArticlesByParam.put("limitCount", limitCount);
		getForPrintArticlesByParam.put("limitFrom", limitFrom);
		int totalCount = articleService.getTotalCount(getForPrintArticlesByParam);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
		
		List<Article> articles = articleService.getForPrintArticles(getForPrintArticlesByParam);
		
		model.addAttribute("articles", articles);

		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalCount", totalCount);
		
		int pageBoundSize = 5;
		int pageStartsWith = page - pageBoundSize;
		if (pageStartsWith < 1) {
			pageStartsWith = 1;
		}
		int pageEndsWith = page + pageBoundSize;
		if (pageEndsWith > totalPage) {
			pageEndsWith = totalPage;
		}

		model.addAttribute("pageStartsWith", pageStartsWith);
		model.addAttribute("pageEndsWith", pageEndsWith);

		boolean beforeMorePages = pageStartsWith > 1;
		boolean afterMorePages = pageEndsWith < totalPage;

		model.addAttribute("beforeMorePages", beforeMorePages);
		model.addAttribute("afterMorePages", afterMorePages);
		model.addAttribute("pageBoundSize", pageBoundSize);

		model.addAttribute("needToShowPageBtnToFirst", page != 1);
		model.addAttribute("needToShowPageBtnToLast", page != totalPage);

		

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
	public String doWrite(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {

		int newArticleId = articleService.write(param);

		String redirectUri = (String) param.get("redirectUri");
		redirectUri = redirectUri.replace("#id", newArticleId + "");

		return "redirect:" + redirectUri;
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
