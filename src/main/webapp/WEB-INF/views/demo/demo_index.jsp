<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html>
<head>
<script>
	
</script>
</head>

<body>
	<div id="demo-lt" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'demos',split:true"
			style="width: 200px;">
			<ul id="demo-lt-ul" class="easyui-tree">
				<li><span>控件演示</span>
					<ul>
						<li><span><a href="javascript:void(0)"
								onclick="openTab4Demo('demo-tab-form','from','<c:url value="/demo/index.do?method=showForm"/>')">form表单</a></span></li>
						<li><span><a href="javascript:void(0)"
								onclick="openTab4Demo('demo-upload-file','file-upload','<c:url value="/demo/FileUpload.do?method=showForm"/>')">文件上传</a></span></li>
						<li><span><a href="javascript:void(0)"
								onclick="openTab4Demo('demo-upload-file','Uploadify','<c:url value="/demo/FileUpload.do?method=showUploadifyForm"/>')">Uploadify上传</a></span></li>
					</ul></li>
				<li><span>表格演示</span>
					<ul>
						<li><span><a href="javascript:void(0)"
								onclick="openTab4Demo('demo-tab-dg-blog','datagrid-blog','<c:url value="/demo/blog.do?method=list"/>')">datagrid</a></span></li>
					</ul></li>
			</ul>
		</div>
		<div data-options="region:'center',title:''">
			<div id="demo-lt-tabs" class="easyui-tabs" data-options="fit:true">
				<div id="demo-tab-index" title="welcome" style="padding: 20px;">
					<p>wellcome to demos</p>
					<p>More see <a href="http://www.micmiu.com" target="_blank">http://www.micmiu.com</a></p>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var tabs_id = "#demo-lt-tabs";
	$(tabs_id).tabs({
		onBeforeClose : function(title) {
			return confirm('是否确定关闭 ' + title + '?');
		}
	});

	function openTab4Demo(tabId, titleName, tabURL) {
		if ($(tabs_id).tabs('exists', titleName)) {
			$(tabs_id).tabs('select', titleName);
			var tab = $(tabs_id).tabs('getSelected');
			tab.panel('refresh');
		} else {
			$(tabs_id).tabs('add', {
				id : tabId,
				title : titleName,
				href : tabURL,
				//content:'<iframe name="if_'+tabId+ '" scrolling="auto" frameborder="0" src="'+tabURL+'" style="width:100%;height:100%;"></iframe>', 
				closable : true,
				closed : true
			});
			//window.open(tabURL,"if_"+tabId);  
		}
	}
</script>
</html>
