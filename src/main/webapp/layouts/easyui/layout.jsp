<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="header.jsp" />
<title><tiles:getAsString name="title" /></title>
</head>
<body>
	<div id="main-layout" class="easyui-layout" fit="true">
		<div data-options="region:'north',split:true" style="height: 50px;"><jsp:include
				page="north.jsp" /></div>
		<div data-options="region:'south',split:false" style="height: 25px;"><jsp:include
				page="footer.jsp" /></div>
		<div data-options="region:'west',title:'导航',split:true"
			style="width: 200px;"><jsp:include page="west.jsp" /></div>
		<div data-options="region:'east',split:true,collapsed:true" title="查看"
			style="width: 300px;">
			<jsp:include page="east.jsp" />
		</div>
		<div
			data-options="region:'center',title:'<tiles:getAsString name="position" />'"
			style="padding: 5px; background: #eee;">
			<jsp:include page="workspace.jsp" /></div>

	</div>

</body>

<script type="text/javascript">
$(function() {
	$(window).resize(function() {
		$('#main-layout').layout('resize');
	});
});
</script>
</html>