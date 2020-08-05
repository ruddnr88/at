package com.sbs.rko.at.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleReply {
	private int id;
	private int articleId;
	private String regDate;
	private String updateDate;
	private boolean displayStatus;
	private String body;
}
