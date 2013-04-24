<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#message").fadeOut(3000);
	});
	$("#input-form").validate({
		errorContainer : "#messageBox"
	});
</script>
</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="success">${message}</div>
	</c:if>
	<form:form id="input-form" modelAttribute="fileBean"
		enctype="multipart/form-data"
		action="${ctx}/demo/FileUpload.do?method=uploadify" method="post">
		<fieldset class="prepend-top">

			<legend>文件上传示例</legend>

			<div id="messageBox" class="error-msg" style="display: none">输入有误，请先更正。</div>

			<div class="field">
				<label for="file" class="field">文件:</label> <input type="file"
					id="file" name="file" size="20" value="${fileBean.file}"
					class="required" />
			</div>
			<div class="field">
				<label for="other" class="field">描述:</label> <input type="text"
					id="other" name="other" size="20" value="${fileBean.other}" />
			</div>

			<div class="field">
				<input type="button" name="btn_submit" value="提交"
					onclick="formSubmit('#input-form')" /><input type="button"
					name="btn_cancel" value="取消"
					onclick="cancelTabForm('#demo-lt-tabs')" />
			</div>
		</fieldset>

	</form:form>
</body>
<script type="text/javascript">
	function formSubmit(_form_id) {
		var actionUrl = $(_form_id).attr("action");
		if (!$(_form_id).valid()) {
			return;
		}
		$(_form_id).submit();
	}

	function cancelTabForm(_tabs_id) {
		var tab = $(_tabs_id).tabs('getSelected');
		var title = tab.panel("options").title
		$(_tabs_id).tabs('close', title);
	}
</script>
</html>
