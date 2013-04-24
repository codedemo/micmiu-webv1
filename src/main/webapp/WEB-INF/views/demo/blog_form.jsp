<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html>
<head>
<script>
	$(document).ready(function() {
		$("#title").focus();
		$("#input-form").validate({
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				if (element.is(":checkbox"))
					error.appendTo(element.parent().next());
				else
					error.insertAfter(element);
			}
		});
	});
</script>
</head>

<body>
	<form:form id="input-form" modelAttribute="blog"
		action="${ctx}/demo/blog.do?method=save" method="post">
		<input type="hidden" name="id" value="${blog.id}" />
		<input type="hidden" name="creater" value="${blog.creater}" />
		<fieldset class="prepend-top">

			<legend>博客信息</legend>

			<div id="messageBox" class="error-msg" style="display: none">输入有误，请先更正。</div>

			<div>
				<label for="title" class="field">标题:</label> <input type="text"
					id="title" name="title" size="20" value="${blog.title}"
					class="required" />
			</div>
			<div>
				<label for="author" class="field">作者:</label> <input type="text"
					id="author" name="author" size="20" value="${blog.author}"
					class="required" />
			</div>
			<div>
				<label for="category" class="field">分类:</label> <input type="text"
					id="category" name="category" size="20" value="${blog.category}"
					class="required" />
			</div>
			<div>
				<label for="url" class="field">地址:</label> <input type="text"
					id="url" name="url" size="20" value="${blog.url}" class="required" />
			</div>
			<div>
				<label for="other" class="field">其他:</label> <input type="text"
					id="other" name="other" size="20" value="${blog.other}"
					class="required" />
			</div>
			<div>
				<label for="publishDate" class="field">发表日期:</label> <input
					type="text" id="publishDate" name="publishDate" size="20"
					value="${blog.publishDate}" class="Wdate required"
					onFocus="WdatePicker({readOnly:true})" />
			</div>
		</fieldset>

	</form:form>
</body>
<script type="text/javascript">
	
</script>
</html>
