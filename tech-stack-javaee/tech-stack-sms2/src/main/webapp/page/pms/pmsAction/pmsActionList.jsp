<%-- 权限模块:权限（功能点）管理:分页查看页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="pmsPermission_pmsActionList.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/config/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			    <td>
					权限名称：<input type="text" name="actionName" value="${actionName}" size="30" alt="模糊查询"  />
				</td>
				<td>
					权限标识：<input type="text" name="act" value="${act}" size="30" alt="精确查询"  />
				</td>
				<td>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		<p:permission value="pms:action:add">
			<li><a class="add" href="pmsPermission_pmsActionAdd.action" target="dialog" width="550" height="350" rel="input" title="添加权限"><span>添加权限</span></a></li>
		</p:permission>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="120">
		<thead>
			<tr>
				<th>序号</th>
				<th>权限名称</th>
				<th>权限标识</th>
				<th>关联的菜单</th>
				<th>描述</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
					<td>${actionName }</td>
					<td>${action }</td>
					<td>${menuName}</td>
					<td>${remark}</td>
					<td>
						<fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd"/>
					</td>
					<td>
					<p:permission value="pms:action:edit">
						[<a href="pmsPermission_pmsActionEdit.action?id=${id}" title="修改权限" target="dialog" width="550" height="300" rel="input"  style="color:blue">修改</a>]
					</p:permission>
					<p:permission value="pms:action:delete">
						&nbsp;[<a href="pmsPermission_pmsActionDel.action?id=${id}" title="删除权限【${action }】" target="ajaxTodo" style="color:blue">删除</a>]
					</p:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="/page/config/pageBar.jsp"%>
</div>
    