package com.sbs.rko.at.controller;

import java.util.List;
import java.util.Map;

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

	@RequestMapping("/article/list")
	public String showList(Model model) {
		

		
		int page = 1;
		String searchKeywordType = "";
		String searchKeyword = "";
		
		
		int itemsInAPage = 10;
		int totalCount = articleService.getTotalCount(searchKeywordType,searchKeyword);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
		
	
		
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);
		
		List<Article> articles = articleService.getForPrintArticles(page, itemsInAPage);
		model.addAttribute("articles", articles);

		return "article/list";
	}

	@RequestMapping("/article/detail")
	public String showDetail(Model model, @RequestParam Map<String, Object> param) {
		int id = Integer.parseInt((String) param.get("id"));

		Article article = articleService.getForPrintArticleById(id);

		model.addAttribute("article", article);
		return "article/detail";
	}

	@RequestMapping("/article/write")
	public String showWrite() {
		return "article/write";
	}

	@RequestMapping("/article/doWrite")
	@ResponseBody
	public String showDoWrite(Model model, @RequestParam Map<String, Object> param) {
		
		articleService.write(param);
		return "html:<script> alert('게시물이 작성되었습니다.'); location.replace('list'); </script>";
	}
	@RequestMapping("/article/delete")
	@ResponseBody
	public String showDelete(Model model, @RequestParam Map<String, Object> param) {
		int id = Integer.parseInt((String) param.get("id"));
		articleService.delete(id);
		return "html:<script> alert('"+id+"번 게시물이 삭제되었습니다.'); location.replace('list'); </script>";
	}
	
	@RequestMapping("/article/modify")
	public String showModify(Model model, @RequestParam Map<String, Object> param,int id) {
		Article article = articleService.getForPrintArticleById(id);
		model.addAttribute("article", article);
		
		return "article/modify";
	}
	@RequestMapping("/article/doModify")
	@ResponseBody
	public String showDoModify(@RequestParam Map<String, Object> param, int id) {
		
		articleService.modify(param);
		return "html:<script> alert('"+id+"번 게시물이 수정되었습니다.'); location.replace('./detail?id="+id+"'); </script>";
	}
}
