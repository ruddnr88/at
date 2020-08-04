<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 상세내용" />
<%@ include file="../part/head.jspf"%>

<div class="table-box con">
	<table>
		<tbody>
			<tr>
				<th>번호</th>
				<td>${article.id}</td>
			</tr>
			<tr>
				<th>날짜</th>
				<td>${article.regDate}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${article.title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${article.body}</td>
			</tr>
		</tbody>
	</table>
	<div class="bnt_direct">
		<div class="common_bnt"><a href="list" >게시글목록</a></div>
		<div class="bnt_right">
			<div class="common_bnt"><a href="delete?id=${article.id}" >삭 제</a></div>
			<div class="common_bnt"><a href="modify?id=${article.id}" >수 정</a></div>
		
		</div>
	</div>

</div>

<%@ include file="../part/foot.jspf"%>