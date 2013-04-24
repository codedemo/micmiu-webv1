<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html>
<head>
<title>用户列表</title>
</head>

<body>
	<table id="dg-list" class="easyui-datagrid" title="用户列表"
		style="width: 700px; height: auto"
		data-options="rownumbers:true,singleSelect:false,iconCls:'icon-table',pagination:true,url:'user.do?method=query',toolbar:'#tb',fit: true">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',width:80,sortable:true">ID</th>
				<th data-options="field:'loginName',width:120,sortable:true">登录名</th>
				<th data-options="field:'name',width:120">姓名</th>
				<th data-options="field:'roleName',width:120">角色</th>
				<th data-options="field:'email',width:200">邮箱</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="user:add">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true"
					onclick="MM_utils.baseWinAdd({win_url:'user.do?method=showForm'})">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="user:edit">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-edit',plain:true"
					onclick="MM_utils.baseWinEdit({win_url:'user.do?method=showForm'});">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="user:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-remove',plain:true"
					onclick="MM_utils.baseGDDel('#dg-list','user.do?method=batchDel')">删除</a>
			</shiro:hasPermission>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true"
				onclick="MM_utils.baseView({win_url:'user.do?method=showView'})">查看</a>
		</div>
		<div>
			<form id="query-form">
				登录名: <input style="width: 80px" name="loginName"> 用户名: <input
					style="width: 80px" name="name"> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-search'" onclick="MM_utils.formQuery()">查询</a>
			</form>
		</div>
	</div>
	<div id="form-win"></div>
</body>
<script type="text/javascript">
	
</script>
</html>
