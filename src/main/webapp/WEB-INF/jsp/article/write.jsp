<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시물 작성" />
<%@ include file="../part/head.jspf"%>
<div class="table-box con">
	<form action="doWrite" method="POST" class="write-form form1"
		onsubmit="ArticleWriteForm__submit(this); return false;">
		<input type="hidden" name="redirectUri" value="/usr/article/detail?id=#id">
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input form-control-box">
				<input name="title" type="text" autofocus placeholder="제목을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input form-control-box">
				<textarea name="body" cols="40" rows="10" placeholder="내용을 입력하세요."></textarea>
			</div>
		</div>
	
		<div class="con_butt" style="margin-top: 10px;">
			<div class="input butt">
				<input type="submit" class="write_bnt" value="전송" /> <input
					class="write_bnt" type="button" onClick="cencle()" value="취소" />
			</div>
		</div>
	</form>
</div>
<script>


	function cencle() {
		if (confirm("취소하시겠습니까?")) {
			location.href = "list"
		} else {
			alert('아님말고');
		}
	}
	function ArticleWriteForm__submit(form) {
	
		form.title.value = form.title.value.trim();
		if (form.title.value.length == 0) {
			alert('제목을 입력해주세요.');
			form.title.focus();
			return;
		}
		form.body.value = form.body.value.trim();
		if (form.body.value.length == 0) {
			alert('내용을 입력해주세요.');
			form.body.focus();
			return false;
		}

		form.submit();
	
	}
</script>
<%@ include file="../part/foot.jspf"%>