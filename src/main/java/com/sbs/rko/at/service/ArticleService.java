package com.sbs.rko.at.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.rko.at.dao.ArticleDao;
import com.sbs.rko.at.dto.Article;
import com.sbs.rko.at.dto.ArticleReply;
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

	public int writeReply(Map<String, Object> param) {
		articleDao.writeReply(param);
		return Util.getAsInt(param.get("articleId"));
	}

	public List<ArticleReply> getForPrintArticleReplies(int articleId) {
		List<ArticleReply> articleReplies = articleDao.getForPrintArticleReplies(articleId);
		return articleReplies;
	}

	public void doArticleReplydelete(int id) {
		articleDao.articleReplyDelete(id);
	}

	public ArticleReply getForPrintArticleReply(int id) {
		ArticleReply articleReply = articleDao.getForPrintArticleReply(id);
		return articleReply;
	}

	public int modifyReply(Map<String, Object> param) {
		articleDao.modifyReply(param);
		
		return Util.getAsInt(param.get("articleId"));
	}

}
