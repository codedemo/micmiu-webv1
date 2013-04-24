<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html>
<head>
</head>
<body>
	<table id="dg-list">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="demo_common:view">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true"
					onclick="MM_utils.baseWinAdd({win_url:'blog.do?method=showForm'})">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="demo_common:view">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-edit',plain:true"
					onclick="MM_utils.baseWinEdit({win_url:'blog.do?method=showForm'});">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="demo_common:view">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-remove',plain:true"
					onclick="MM_utils.baseGDDel('#dg-list','blog.do?method=batchDel')">删除</a>
			</shiro:hasPermission>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true"
				onclick="MM_utils.baseView({win_url:'blog.do?method=getViewData'})">查看</a>
		</div>
		<div>
			<form id="query-form">
				角色: <input style="width: 80px" name="roleName"> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-search'" onclick="MM_utils.formQuery()">查询</a>
			</form>
		</div>
	</div>
	<div id="form-win"></div>
</body>
<script type="text/javascript">

var dgColumns;
$.ajax({
	  url: "blog.do?method=getGridColumns",
	  async:false,
	  success: function(data){
		  dgColumns = data;
	  },
	  dataType: "json"
	});
 
$('#dg-list').datagrid({
	title:'博客列表',
    url:'blog.do?method=query',
    toolbar:'#tb',
    fit: true,
    rownumbers:true,
    singleSelect:false,
    iconCls:'icon-table',
    pagination:true,
    sortName: 'title',
    frozenColumns:[[
                    {field:'ck',checkbox:true}  
				]],
    columns: new Array(dgColumns)
}); 
</script>
</html>
