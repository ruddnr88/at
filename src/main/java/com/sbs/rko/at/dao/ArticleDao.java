package com.sbs.rko.at.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.rko.at.dto.Article;

@Mapper
public interface ArticleDao {
	List<Article> getForPrintArticles(int limitFrom, int itemsInAPage);

	Article getForPrintArticleById(@Param("id") int id);

	void write(Map<String, Object> param);

	void delete(int id);

	void modify(Map<String, Object> param);

	int getTotalCount(String searchKeywordType, String searchKeyword);

	

}
