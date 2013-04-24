<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html>
<head>
<script>
	
</script>
</head>

<body>
	<form:form id="input-form" modelAttribute="user"
		action="${ctx}/system/user.do?method=save" method="post">
		<input type="hidden" name="id" value="${user.id}" />
		<fieldset class="prepend-top">

			<legend>用户信息</legend>

			<div id="messageBox" class="error-msg" style="display: none">输入有误，请先更正。</div>

			<div>
				<label for="loginName" class="field">登录名:</label> <input type="text"
					id="loginName" name="loginName" size="20" value="${user.loginName}"
					class="required" />
			</div>
			<div>
				<label for="name" class="field">用户名:</label> <input type="text"
					id="name" name="name" size="20" value="${user.name}"
					class="required" minlength="3" />
			</div>
			<div>
				<label for="password" class="field">密码:</label> <input
					type="password" id="password" name="password" size="20"
					value="${user.password}" class="required" minlength="3" />
			</div>
			<div>
				<label for="passwordConfirm" class="field">确认密码:</label> <input
					type="password" id="passwordConfirm" name="passwordConfirm"
					size="20" value="${user.password}" equalTo="#password" />
			</div>
			<div>
				<label for="email" class="field">邮箱:</label> <input type="text"
					id="email" name="email" size="40" value="${user.email}"
					class="email" />
			</div>
			<div>
				<label for="other" class="field">备注:</label>
				<textarea rows="3" cols="30" id="other" name="other"
					style="height: 50px; width: 250px"></textarea>
			</div>
		</fieldset>

	</form:form>
</body>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//聚焦第一个输入框
						$("#loginName").focus();
						//为inputForm注册validate函数
						$("#input-form")
								.validate(
										{
											rules : {
												loginName : {
													remote : "${ctx}/system/user.do?method=checkLoginName&oldLoginName="
															+ encodeURIComponent('${user.loginName}')
												}
											},
											messages : {
												loginName : {
													remote : "用户登录名已存在"
												},
												passwordConfirm : {
													equalTo : "输入与上面相同的密码"
												}
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												if (element.is(":checkbox"))
													error.appendTo(element
															.parent().next());
												else
													error.insertAfter(element);
											}
										});
					});
</script>
</html>
