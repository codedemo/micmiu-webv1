<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<div id="east-p" class="easyui-panel"
	data-options="fit:true,border:false">
	<table id="pg-view" class="easyui-propertygrid"
		data-options="fit:true,border:false,showGroup:true,nowrap:false,columns: mycolumns"></table>
</div>
<script type="text/javascript">
	var mycolumns = [ [ {
		field : 'name',
		title : '属性',
		width : 100,
		sortable : true
	}, {
		field : 'value',
		title : '值',
		width : 100,
		resizable : false
	} ] ];
</script>