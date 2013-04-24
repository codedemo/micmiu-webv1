<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html>
<head>
<script>
	
</script>
</head>

<body>
	<form:form id="input-form" modelAttribute="role"
		action="${ctx}/system/role.do?method=save" method="post">
		<input type="hidden" name="id" value="${role.id}" />
		<fieldset class="prepend-top">

			<legend>用户信息</legend>

			<div id="messageBox" class="error-msg" style="display: none">输入有误，请先更正。</div>

			<div>
				<label for="roleName" class="field">角色:</label> <input type="text"
					id="roleName" name="roleName" size="20" value="${role.roleName}"
					class="required" />
			</div>
			<div>
				<label for="authList" class="field">权限:</label>
				<form:checkboxes path="authList" items="${permissionMap}" />
				<div></div>
			</div>
		</fieldset>

	</form:form>
</body>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#roleName").focus();
						$("#input-form")
								.validate(
										{
											rules : {
												roleName : {
													remote : "${ctx}/system/role.do?method=checkRoleName&oldRoleName="
															+ encodeURIComponent('${role.roleName}')
												},
												authList : "required"
											},
											messages : {
												roleName : {
													remote : "角色名已存在"
												},
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												if (element.is(":checkbox")){
													error.appendTo(element.parent().next());
												}else
													error.insertAfter(element);
											}
										});
					});
</script>
</html>
