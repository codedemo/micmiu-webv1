var MM_utils = MM_utils || {
	version : '0.1',
	author : 'Michael',
	blog : 'www.micmiu.com'
}

MM_utils.baseWinAdd = function(paras) {
	MM_Ex.formWin(paras);
};

MM_utils.baseWinEdit = function(paras) {
	var _dg_id = paras.dg_id ? paras.dg_id : "#dg-list";
	var rows = $(_dg_id).datagrid('getSelections');
	if (rows == '' || rows.length > 1) {
		$.messager.alert('提示', '有且只能选中一条记录进行编辑', '提示');
		return;
	}
	var id = rows[0].id;
	paras.win_url = paras.win_url + '&id=' + id
	MM_Ex.formWin(paras);
};

MM_utils.baseGDDel = function(dg_id, del_url) {
	var rows = $(dg_id).datagrid('getSelections');
	if (rows == '' || rows.length == 0) {
		$.messager.alert('提示', '至少选中一条记录进行删除', '提示');
		return;
	}
	var ids = [];
	for ( var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
	}
	var del_url_ids = del_url + '&ids=' + ids.join(',');

	$.messager.confirm('提示', '确定要删除吗?', function(result) {
		if (result) {
			$.post(del_url_ids, function(msg) {
				$.messager.alert('提示', msg, '提示');
				$(dg_id).datagrid('reload');
				$(dg_id).datagrid('clearSelections');
			});
		}
	});
};

MM_utils.formQuery = function(paras) {
	var paras = paras||{};
	var _dg_id = paras.dg_id?paras.dg_id:"#dg-list";
	var _form_id = paras.form_id?paras.form_id:"#query-form";
	var params = $(_dg_id).datagrid('options').queryParams; 
	var fields = $(_form_id).serializeArray();
	$.each(fields, function(i, field) {
		params[field.name] = field.value;
	});
	$(_dg_id).datagrid('reload'); 
};

MM_utils.baseView = function(paras) {
	var _dg_id = paras.dg_id ? paras.dg_id : "#dg-list";
	var _pg_id = paras.p_id ? paras.pg_id : "#pg-view";
	var rows = $(_dg_id).datagrid('getSelections');
	if (rows == '' || rows.length > 1) {
		$.messager.alert('提示', '有且只能选中一条记录进行查看', '提示');
		return;
	}
	var id = rows[0].id;
	paras.win_url = paras.win_url + '&id=' + id;
	$(_pg_id).propertygrid({  
	    url: paras.win_url,
	    showGroup: true,
	    scrollbarSize: 0  
	});
	$('#main-layout').layout('expand','east');  
};
