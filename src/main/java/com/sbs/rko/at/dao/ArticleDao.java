package com.sbs.rko.at.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.rko.at.dto.Article;

@Mapper
public interface ArticleDao {
	public List<Article> getForPrintArticles(int limitFrom, int itemsInAPage);

	public Article getForPrintArticleById(@Param("id") int id);

	public void wrtie(Map<String, Object> param);

	public void delete(int id);

	public void modify(Map<String, Object> param);

	public int getTotalCount(String searchKeywordType, String searchKeyword);


}
