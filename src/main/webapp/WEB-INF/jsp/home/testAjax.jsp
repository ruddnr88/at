<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="테스트Ajax" />
<%@ include file="../part/head.jspf"%>
<script>
function Article__loadArticleInfo() {
	$.get(
		'/home/getDataTestAjax',
		{},
		function(data) {
			$('.article-title').empty().append(data.title);
			$('.article-body').empty().append(data.body);
		}
	);
}
</script> 
<div class="con">
	<h2>데이터가져오기</h2>
	<a href="/home/getDataTestAjax">무식하게 가져오기</a>
	<a href="#" onclick="Article__loadArticleInfo(); return false;">우아하게 가져오기</a>
	<h2>게시물정보</h2>
	제목 : <span class="article-title"></span>
	<br>
	내용 : <span class="article-body"></span>
</div>
<%@ include file="../part/foot.jspf"%>