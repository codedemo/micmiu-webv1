<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html>
<head>
</head>
<body>
	<table id="dg-list" class="easyui-datagrid" title="角色列表"
		style="width: 700px; height: auto"
		data-options="rownumbers:true,singleSelect:false,iconCls:'icon-table',pagination:true,url:'role.do?method=query',toolbar:'#tb',fit: true">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',width:80,sortable:true">ID</th>
				<th data-options="field:'roleName',width:120,sortable:true">角色名称</th>
				<th data-options="field:'permissionNames',width:500,sortable:false">权限</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="role:add">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true"
					onclick="MM_utils.baseWinAdd({win_url:'role.do?method=showForm'})">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="role:edit">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-edit',plain:true"
					onclick="MM_utils.baseWinEdit({win_url:'role.do?method=showForm'});">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="role:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-remove',plain:true"
					onclick="MM_utils.baseGDDel('#dg-list','role.do?method=batchDel')">删除</a>
			</shiro:hasPermission>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true"
				onclick="MM_utils.baseView({win_url:'role.do?method=showView'})">查看</a>
		</div>
		<div>
			<form id="query-form">
				角色: <input style="width: 80px" name="roleName">  <a
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
