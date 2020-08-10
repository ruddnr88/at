package com.sbs.rko.at.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.rko.at.dao.ArticleDao;
import com.sbs.rko.at.dto.Article;
import com.sbs.rko.at.dto.ArticleReply;
import com.sbs.rko.at.dto.Member;
import com.sbs.rko.at.dto.ResultData;
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
		articleDao.writeArticleReply(param);
		return Util.getAsInt(param.get("id"));
	}

	public List<ArticleReply> getForPrintArticleReplies(@RequestParam Map<String, Object> param) {
		List<ArticleReply> articleReplies = articleDao.getForPrintArticleReplies(param);
		Member actor = (Member) param.get("actor");

		for (ArticleReply articleReply : articleReplies) {
			updateForPrintInfo(actor, articleReply);
		}

		return articleReplies;
	}

	private void updateForPrintInfo(Member actor, ArticleReply articleReply) {
		articleReply.getExtra().put("actorCanDelete", actorCanDelete(actor, articleReply));
		articleReply.getExtra().put("actorCanModify", actorCanModify(actor, articleReply));
	}

	// 액터가 해당 댓글을 수정할 수 있는지 알려준다.
	public boolean actorCanModify(Member actor, ArticleReply articleReply) {
		return actor != null && actor.getId() == articleReply.getMemberId() ? true : false;
	}

	// 액터가 해당 댓글을 삭제할 수 있는지 알려준다.
	public boolean actorCanDelete(Member actor, ArticleReply articleReply) {
			return actorCanModify(actor, articleReply);
	}

	public void deleteReply(int id) {
		articleDao.deleteReply(id);
	}

	public ArticleReply getForPrintArticleReply(int id) {
		ArticleReply articleReply = articleDao.getForPrintArticleReply(id);
		return articleReply;
	}

	public ResultData modifyReply(Map<String, Object> param) {
		articleDao.modifyReply(param);
		return new ResultData("S-1", String.format("%d번 댓글을 수정하였습니다.", Util.getAsInt(param.get("id"))), param);
	}

	public ArticleReply getForPrintArticleReplyById(int id) {
		return articleDao.getForPrintArticleReplyById(id);
	}

}
