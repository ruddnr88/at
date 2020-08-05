package com.sbs.rko.at.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.rko.at.dto.Article;

@Controller
public class HomeController {
	@RequestMapping("/home/main")
	public String showMain() {
		return "home/main";
	}
	@RequestMapping("/home/testAjax")
	public String showtestAjax() {
		return "home/testAjax";
	}
	@RequestMapping("/home/getDataTestAjax")
	@ResponseBody
	public Article getDataTestAjax() {
		Article article = new Article(1,"2020-12-12 12:12:12","2020-12-12 12:12:12",true, "2020-12-12 12:12:12", false, "안녕하세융:)", "하하하하하 ");
		return article;
	}
}
