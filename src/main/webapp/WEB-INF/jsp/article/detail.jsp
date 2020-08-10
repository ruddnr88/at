<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 상세내용" />
<%@ include file="../part/head.jspf"%>
<style>
.article-reply-modify-form-modal {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(0, 0, 0, 0.4);
	display: none;
}
.article-reply-modify-form-modal-actived .article-reply-modify-form-modal {
	display: flex;
}
</style>
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
		<div class="common_bnt">
			<a href="list">게시글목록</a>
		</div>
		<div class="bnt_right">
			<div class="common_bnt">
				<a href="delete?id=${article.id}">삭 제</a>
			</div>
			<div class="common_bnt">
				<a href="modify?id=${article.id}">수 정</a>
			</div>

		</div>
	</div>

	<script>
		function ArticleWriteReplyForm__submit(form) {
			form.body.value = form.body.value.trim();
			if (form.body.value.length == 0) {
				alert('댓글을 입력해주세요.');
				form.body.focus();
				return;
			}
			$.post('./doWriteReplyAjax', {
				articleId : param.id,
				body : form.body.value
			}, function(data) {
				
			}, 'json');
			form.body.value = '';
		}
	</script>
	<h2>댓글작성</h2>
	<form class="write-form form1" action=""
		onsubmit="ArticleWriteReplyForm__submit(this); return false;">
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<textarea name="body" rows="5" style="width:100%;"placeholder="댓글내용을 입력하세요."></textarea>
			</div>
		</div>
		<div class="con_butt" style="margin-top: 10px;">
			<div class="input btn">
				<input type="submit" class="write_bnt" value="전송" />
			</div>
		</div>
	</form>
	<h2>댓글리스트</h2>
		<table class="article-reply-list-box reply_list">
			<colgroup>
				<col width="50" />
				<col width="120" />
				<col width="80">
				<col width="400" />
				<col width="80" />
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>날짜</th>
					<th>작성자</th>
					<th>내용</th>
					<th>비고</th>
				</tr>
			</thead>
			<tbody>
			
			</tbody>
		</table>
	
	<div class="article-reply-modify-form-modal flex flex-ai-c flex-jc-c">
		<form action="" class="form1 bg-white padding-10"
			onsubmit="ArticleReplyList__submitModifyForm(this); return false;">
			<input type="hidden" name="id" />
			<div class="form-row">
				<div class="form-control-label">내용</div>
				<div class="form-control-box">
					<textarea name="body" placeholder="내용을 입력해주세요."></textarea>
				</div>
			</div>
			<div class="form-row">
				<div class="form-control-label">수정</div>
				<div class="form-control-box">
					<button type="submit">수정</button>
					<button type="button"
						onclick="ArticleReplyList__hideModifyFormModal();">취소</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script>
var ArticleReplyList__$box = $('.article-reply-list-box');
var ArticleReplyList__$tbody = ArticleReplyList__$box.find('tbody');
var ArticleReplyList__lastLodedId = 0;
var ArticleReplyList__submitModifyFormDone = false;

function ArticleReplyList__submitModifyForm(form) {
	if (ArticleReplyList__submitModifyFormDone) {
		alert('처리중입니다.');
		return;
	}
	form.body.value = form.body.value.trim();
	if (form.body.value.length == 0) {
		alert('내용을 입력해주세요.');
		form.body.focus();
		return;
	}

	var id = form.id.value;
	var body = form.body.value;
	ArticleReplyList__submitModifyFormDone = true;
		$.post('doModifyReplyAjax', {
			id :id,
			body : body
		}, function(data) {
			if (data.resultCode && data.resultCode.substr(0, 2) == 'S-') {
				// 성공시에는 기존에 그려진 내용을 수정해야 한다.!!
				var $tr = $('.article-reply-list-box tbody > tr[data-id="' + id+ '"] .article-reply-body');
				$tr.empty().append(body);
			}
			ArticleReplyList__hideModifyFormModal();
			ArticleReplyList__submitModifyFormDone = false;
		}, 'json');
	}
	function ArticleReplyList__showModifyFormModal(el) {
		$('html').addClass('article-reply-modify-form-modal-actived');
		var $tr = $(el).closest('tr');
		var originBody = $tr.data('data-originBody');
		var id = $tr.attr('data-id');
		var form = $('.article-reply-modify-form-modal form').get(0);
		form.id.value = id;
		form.body.value = originBody;
	}
	function ArticleReplyList__hideModifyFormModal() {
		$('html').removeClass('article-reply-modify-form-modal-actived');
	}
	function ArticleReplyList__loadMoreCallback(data) {
		if (data.body.articleReplies && data.body.articleReplies.length > 0) {
			ArticleReplyList__lastLodedId = data.body.articleReplies[data.body.articleReplies.length - 1].id;
			ArticleReplyList__drawReplies(data.body.articleReplies);
		}
		setTimeout(ArticleReplyList__loadMore, 2000);
	}

	
	function ArticleReplyList__loadMore() {
		$.get('getForPrintArticleReplies', {
			articleId : param.id,
			from : ArticleReplyList__lastLodedId + 1
		}, ArticleReplyList__loadMoreCallback, 'json');
	}
	function ArticleReplyList__drawReplies(articleReplies) {
		for (var i = 0; i < articleReplies.length; i++) {
			var articleReply = articleReplies[i];
			ArticleReplyList__drawReply(articleReply);
		}
	}
	function ArticleReplyList__delete(el) {
		if ( confirm('삭제?') == false ) {
			 return; 
			 }
		var $tr = $(el).closest('tr');
		var id = $tr.attr('data-id');
		 $.post('./doDeleteReplyAjax',{
			 	id:id
			 },function(data) {
				 $tr.remove();
			},'json');
	}
	function ArticleReplyList__drawReply(articleReply) {
		var html = '';
		html += '<tr data-id="' + articleReply.id + '">';
		html += '<td>' + articleReply.id + '</td>';
		html += '<td>' + articleReply.regDate + '</td>';
		html += '<td>' + articleReply.extra.writer + '</td>';
		html += '<td class="article-reply-body">' + articleReply.body + '</td>';
		html += '<td>';
		if (articleReply.extra.actorCanDelete) {
			html += '<button type="button" onclick="ArticleReplyList__delete(this);">삭제 /</button>'; 
		}
		if (articleReply.extra.actorCanModify) {
			html += '<button type="button" onclick="ArticleReplyList__showModifyFormModal(this);">  수정</button>';
		}
		html += '</td>';
		html += '</tr>';
		
		var $tr = $(html);
		$tr.data('data-originBody', articleReply.body);
		ArticleReplyList__$tbody.prepend($tr);
	}
	ArticleReplyList__loadMore();
</script>



<%@ include file="../part/foot.jspf"%>