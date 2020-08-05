<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 리스트" />
<%@ include file="../part/head.jspf"%>

<div class="table-box con">
	<a href="/home/main">메인</a>
	<h3>총 게시물수 : ${totalCount}</h3>
	<div class="flex w_bnt_sbar">
		<div class="common_bnt" style="margin:0;"><a href="write" >게시글 작성</a></div>
		<div class="searchbar">
			<form action="/article/list">
				<input type="hidden" name="page" value="1" />
				<input type="hidden" name="searchKeywordType" value="title" /> 
				<input type="text" name="searchKeyword" value="${param.searchKeyword}" placeholder="검색어를 입력하세요" class="search">
				<button>
					<i class="fa fa-search"></i>
				</button>
			</form>
		</div>
	</div>
	<table>
		<colgroup>
			<col width="100" />
			<col width="200" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>제목</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articles}" var="article">
				<tr>
					<td>${article.id}</td>
					<td>${article.regDate}</td>
					<td><a href="detail?id=${article.id}">${article.title}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		<ul class="flex flex-jc-c listing">
			<c:forEach var="i" begin="1" end="${totalPage}" step="1">
			<li class="${i == cPage ? 'current' : ''}"><a
				href="?page=${i}"  >${i}</a></li>
			</c:forEach>

		</ul>

	
</div>


<%@ include file="../part/foot.jspf"%>