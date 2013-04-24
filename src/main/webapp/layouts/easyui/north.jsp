<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<div>
	<div id="title">
		<div class="title left">开发框架(Michael)</div>
		<div style="float:right;">
			<span class="subtitle right">你好,
				${loninName} <shiro:principal property="name"/>!! <a href="${ctx}/logout.do">&lt;安全退出&gt;</a></span>
		</div>
	</div>
</div>
