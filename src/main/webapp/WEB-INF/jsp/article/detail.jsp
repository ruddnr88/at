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
	<h2>댓글작성</h2>
	<form action="doWriteReply" method="POST" class="write-form form1"
		onsubmit="submitWriteReplyForm(this); return false;">
		<input type="hidden" name="redirectUrl" value="/article/detail?id=#id">
		<input type="hidden" name="articleId" value="${article.id}"/>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<textarea name="body" cols="40" rows="5" placeholder="댓글내용을 입력하세요."></textarea>
			</div>
		</div>
		<div class="con_butt" style="margin-top: 10px;">
			<div class="input btn">
				<input type="submit" class="write_bnt" value="전송" /> <input
					class="write_bnt" type="button" onClick="cencle()" value="취소" />
			</div>
		</div>
	</form>
	<h2>댓글리스트</h2>
	<table class="reply_list">
		<colgroup>
			<col width="50" />
			<col width="100" />
			<col width="400" />
			<col width="80" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>내용</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articleReplies}" var="articleReply">
				<tr>
					<td>${articleReply.id}</td>
					<td>${articleReply.regDate}</td>
					<td>${articleReply.body}</td>
					<td>
						<a href="./doDeleteReply?id=${articleReply.id}"
						onclick="if ( confirm('삭제?') == false ) { return false; }">삭제</a>
						<a href="./modifyReply?id=${articleReply.id}">수정</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>
<script>
	var submitWriteReplyForm = false;

	function cencle() {
		if (confirm("취소하시겠습니까?")) {
			location.href = "list"
		} else {
			alert('아님말고');
		}
	}
	function submitWriteReplyForm(form) {
		if (submitWriteFormDone) {
			alert('처리중입니다.');
			return;
		}
		form.body.value = form.body.value.trim();
		if (form.body.value.length == 0) {
			alert('내용을 입력해주세요.');
			form.body.focus();
			return false;
		}

		form.submit();
		submitWriteReplyForm = true;
	}
</script>

<%@ include file="../part/foot.jspf"%>