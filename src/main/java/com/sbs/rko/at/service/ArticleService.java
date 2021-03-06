package com.sbs.rko.at.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.rko.at.dao.ArticleDao;
import com.sbs.rko.at.dto.Article;
import com.sbs.rko.at.dto.File;
import com.sbs.rko.at.util.Util;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private FileService fileService;

	public List<Article> getForPrintArticles(Map<String, Object> param) {

		List<Article> articles = articleDao.getForPrintArticles(param);

		return articles;
	}

	public Article getForPrintArticleById(int id) {
		Article article = articleDao.getForPrintArticleById(id);
		List<File> files = fileService.getFilesMapKeyFileNo("article", article.getId(), "common", "attachment");

		Map<String, File> filesMap = new HashMap<>();

		for (File file : files) {
			filesMap.put(file.getFileNo() + "", file);
		}

		if (article.getExtra() == null) {
			article.setExtra(new HashMap<>());
		}

		article.getExtra().put("file__common__attachment", filesMap);

		return article;
	}

	public int write(Map<String, Object> param) {
		articleDao.write(param);
		int id = Util.getAsInt(param.get("id"));

		String fileIdsStr = (String) param.get("fileIdsStr");

		if (fileIdsStr != null && fileIdsStr.length() > 0) {
			List<Integer> fileIds = Arrays.asList(fileIdsStr.split(",")).stream().map(s -> Integer.parseInt(s.trim()))
					.collect(Collectors.toList());

			// 파일이 먼저 생성된 후에, 관련 데이터가 생성되는 경우에는, file의 relId가 일단 0으로 저장된다.
			// 그것을 뒤늦게라도 이렇게 고처야 한다.
			for (int fileId : fileIds) {
				fileService.changeRelId(fileId, id);
			}
		}
		return id;
	}

	public void delete(int id) {
		articleDao.delete(id);
	}

	public void modify(Map<String, Object> param) {
		articleDao.modify(param);
	}

	public List<Article> getForPrintArticlesByParam(Map<String, Object> param) {
		List<Article> articles = articleDao.getForPrintArticles(param);

		return articles;
	}


	public int getTotalCount(Map<String, Object> param) {
		return articleDao.getTotalCount(param);
	}

}
