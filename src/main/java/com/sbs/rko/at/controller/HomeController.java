package com.sbs.rko.at.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.rko.at.dto.Article;

@Controller
public class HomeController {
	@RequestMapping("/usr/home/main")
	public String showMain(Model model, HttpSession session) {
		return "home/main";
	}
	@RequestMapping("/usr/home/testAjax")
	public String showtestAjax() {
		return "home/testAjax";
	}
	@RequestMapping("/usr/home/getDataTestAjax")
	@ResponseBody
	public Article getDataTestAjax() {
		Article article = new Article(1,"2020-12-12 12:12:12","2020-12-12 12:12:12",true, "2020-12-12 12:12:12", false, "안녕하세융:)", "하하하하하 ");
		return article;
	}
}
