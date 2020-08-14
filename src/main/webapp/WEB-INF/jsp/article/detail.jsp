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

.article-reply-modify-form-modal-actived .article-reply-modify-form-modal
	{
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
			<c:if test="${article.extra.file__common__attachment['1'] != null}">
				<tr>
					<th>첨부 파일 1</th>
					<td>
						<div class="video-box">
							<video controls
								src="/usr/file/streamVideo?id=${article.extra.file__common__attachment['1'].id}">
							</video>
						</div>
					</td>
				</tr>
			</c:if>
			<c:if test="${article.extra.file__common__attachment['2'] != null}">
				<tr>
					<th>첨부 파일 2</th>
					<td>
						<div class="video-box">
							<video controls
								src="/usr/file/streamVideo?id=${article.extra.file__common__attachment['2'].id}">
							</video>
						</div>
					</td>
				</tr>
			</c:if>
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
		var ArticleWriteReplyForm__submitDone = false;
		function ArticleWriteReplyForm__submit(form) {
			if ( ArticleWriteReplyForm__submitDone ) {
				alert('처리중입니다.');
			}
			form.body.value = form.body.value.trim();
			if (form.body.value.length == 0) {
				alert('댓글을 입력해주세요.');
				form.body.focus();
				return;
			}
			ArticleWriteReplyForm__submitDone = true;
			var startUploadFiles = function(onSuccess) {
				if ( form.file__reply__0__common__attachment__1.value.length == 0 && form.file__reply__0__common__attachment__2.value.length == 0 ) {
					onSuccess();
					return;
				}

				var fileUploadFormData = new FormData(form); 
				
				fileUploadFormData.delete("relTypeCode");
				fileUploadFormData.delete("relId");
				fileUploadFormData.delete("body");
				
				$.ajax({
					url : './../file/doUploadAjax',
					data : fileUploadFormData,
					processData : false,
					contentType : false,
					dataType:"json",
					type : 'POST',
					success : onSuccess
				});
			}
			var startWriteReply = function(fileIdsStr, onSuccess) {
				$.ajax({
					url : './../reply/doWriteReplyAjax',
					data : {
						fileIdsStr: fileIdsStr,
						body: form.body.value,
						relTypeCode: form.relTypeCode.value,
						relId: form.relId.value
					},
					dataType:"json",
					type : 'POST',
					success : onSuccess
				});
			};
			startUploadFiles(function(data) {
				var idsStr = '';
				if ( data && data.body && data.body.fileIdsStr ) {
					idsStr = data.body.fileIdsStr;
				}
				startWriteReply(idsStr, function(data) {
					if ( data.msg ) {
						alert(data.msg);
					}
					form.body.value = '';
					form.file__reply__0__common__attachment__1.value = '';
					form.file__reply__0__common__attachment__2.value = '';

					ArticleWriteReplyForm__submitDone = false;
				});
			});
		}
	</script>
	<c:if test="${isLogined}">
	<h2>댓글작성</h2>
	<form class="write-form form1"
		onsubmit="ArticleWriteReplyForm__submit(this); return false;">
		<input type="hidden" name="relTypeCode" value="article" /> <input
			type="hidden" name="relId" value="${article.id}" />
		<div class="form-row">
			<div class="label">내용</div>
				<textarea name="body" rows="5" style="width: 100%;"
					placeholder="댓글내용을 입력하세요."></textarea>
		</div>
		<div class="con_butt flex flex-jc-e" style="margin-top: 10px;">
			<div class="form-control-box">
				<input type="file" accept="video/*"
								name="file__reply__0__common__attachment__1"
					value="첨부1" />
			</div>
			<div class="form-control-box">
				<input type="file" accept="video/*" name="file__reply__0__common__attachment__2"	value="첨부2" />
			</div>
			<div class="form-control-box">
				<input type="submit" class="write_bnt" value="전송" />
			</div>
		</div>
	</form>
	</c:if>
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
			onsubmit="ReplyList__submitModifyForm(this); return false;">
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
					<button type="button" onclick="ReplyList__hideModifyFormModal();">취소</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script>
	var ReplyList__$box = $('.article-reply-list-box');
	var ReplyList__$tbody = ReplyList__$box.find('tbody');
	var ReplyList__lastLodedId = 0;
	var ReplyList__submitModifyFormDone = false;

	function ReplyList__submitModifyForm(form) {
		if (ReplyList__submitModifyFormDone) {
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

		ReplyList__submitModifyFormDone = true;
		$.post('doModifyReplyAjax', {
			id : id,
			body : body
		}, function(data) {
			if (data.resultCode && data.resultCode.substr(0, 2) == 'S-') {
				// 성공시에는 기존에 그려진 내용을 수정해야 한다.!!
				var $tr = $('.reply-list-box tbody > tr[data-id="' + id + '"] .reply-body')
				$tr.empty().append(body);
			}
			ReplyList__hideModifyFormModal();
			ReplyList__submitModifyFormDone = false;
		}, 'json')
	}
	function ReplyList__showModifyFormModal(el) {
		$('html').addClass('article-reply-modify-form-modal-actived');
		var $tr = $(el).closest('tr');
		var originBody = $tr.data('data-originBody');
		var id = $tr.attr('data-id');
		var form = $('.article-reply-modify-form-modal form').get(0);
		form.id.value = id;
		form.body.value = originBody;
	}
	function ReplyList__hideModifyFormModal() {
		$('html').removeClass('article-reply-modify-form-modal-actived');
	}

	function ReplyList__loadMoreCallback(data) {
		if (data.body.replies && data.body.replies.length > 0) {
			ReplyList__lastLodedId = data.body.replies[data.body.replies.length - 1].id;
			ReplyList__drawReplies(data.body.replies);
		}
		setTimeout(ReplyList__loadMore, ReplyList__loadMoreInterval);
	}
	function ReplyList__loadMore() {
		$.get('getForPrintReplies', {
			articleId : param.id,
			from : ReplyList__lastLodedId + 1
		}, ReplyList__loadMoreCallback, 'json');
	}
	function ReplyList__drawReplies(replies) {
		for (var i = 0; i < replies.length; i++) {
			var reply = replies[i];
			ReplyList__drawReply(reply);
		}
	}
	function ReplyList__delete(el) {
		if (confirm('삭제 하시겠습니까?') == false) {
			return;
		}
		var $tr = $(el).closest('tr');
		var id = $tr.attr('data-id');
		$.post('./doDeleteReplyAjax', {
			id : id
		}, function(data) {
			$tr.remove();
		}, 'json');
	}
	//10초
	ReplyList__loadMoreInterval = 10 * 1000;
	
	function ReplyList__drawReply(reply) {
		var html = '';
		html += '<tr data-id="' + reply.id + '">';
		html += '<td>' + reply.id + '</td>';
		html += '<td>' + reply.regDate + '</td>';
		html += '<td>' + reply.extra.writer + '</td>';
		html += '<td>';
		html += '<div class="reply-body">' + reply.body + '</div>';
		if ( reply.extra.file__common__attachment ) {
			for ( var no in reply.extra.file__common__attachment ) {
				var file = reply.extra.file__common__attachment[no];
	            html += '<div class="video-box"><video controls src="/usr/file/streamVideo?id=' + file.id + '">video not supported</video></div>';				
			}
        }
		
		html += '</td>';
		html += '<td>';
		if (reply.extra.actorCanDelete) {
			html += '<button type="button" onclick="ReplyList__delete(this);">삭제</button>';
		}
		
		if (reply.extra.actorCanModify) {
			html += '<button type="button" onclick="ReplyList__showModifyFormModal(this);">수정</button>';
		}
		
		html += '</td>';
		html += '</tr>';
		var $tr = $(html);
		$tr.data('data-originBody', reply.body);
		ReplyList__$tbody.prepend($tr);
	}
	ReplyList__loadMore();
</script>



<%@ include file="../part/foot.jspf"%>