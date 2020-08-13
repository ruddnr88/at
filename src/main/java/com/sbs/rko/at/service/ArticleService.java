package com.sbs.rko.at.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.rko.at.dao.ArticleDao;
import com.sbs.rko.at.dto.Article;
import com.sbs.rko.at.util.Util;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public List<Article> getForPrintArticles(int page, int itemsInAPage) {
		int limitFrom = (page - 1) * itemsInAPage;

		List<Article> articles = articleDao.getForPrintArticles(limitFrom, itemsInAPage);

		return articles;
	}

	public Article getForPrintArticleById(int id) {
		Article article = articleDao.getForPrintArticleById(id);
		

		return article;
	}

	public int write(Map<String, Object> param) {
		articleDao.write(param);
		return Util.getAsInt(param.get("id"));
	}

	public void delete(int id) {
		articleDao.delete(id);
	}

	public void modify(Map<String, Object> param) {
		articleDao.modify(param);
	}

	public int getTotalCount(String searchKeywordType, String searchKeyword) {
		return articleDao.getTotalCount(searchKeywordType, searchKeyword);
	}


}
