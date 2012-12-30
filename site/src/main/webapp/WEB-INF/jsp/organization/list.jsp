<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page
	import="org.codebone.framework.BaseModel,java.util.List,org.codebone.security.organization.Organization,org.codebone.security.authorities.Authorities"%>
<%@ page
	import="java.lang.reflect.Field,java.lang.reflect.Method,javax.persistence.Id,java.util.Date,org.codebone.framework.util.PagingNavigation"%>
<%
	BaseModel model = (BaseModel) request.getAttribute("data");
	List<Organization> list = (List<Organization>) model.getData();
	BaseModel authModel = (BaseModel) request
			.getAttribute("authorities");
	List<Authorities> authList = (List<Authorities>) authModel
			.getData();
	boolean hasNext = model.isHasNext();
	int allCount = model.getAllCount();
	int currentPage = (Integer) request.getAttribute("page");
	Long organizationIdx = (Long) request
			.getAttribute("organizationIdx");
%>
<!DOCTYPE HTML>
<html>
<head>
<script>
	$(document).ready(function() {
		$('.dropdown-toggle').dropdown()
	});
</script>
<title><%=Organization.class.getSimpleName()%> List</title>
</head>
<body>
	<div class="row">
		<form class="well form-search"
			action="<%=request.getContextPath()%>/app/organization/search"
			method="post">
			<div class="controls" style="text-align: right">
				<select id="organization_search_select" name="property">
					<option>idx</option>
					<option>name</option>
					<option>description</option>
				</select> <input type="text" class="input-xlarge search-query" name="keyword">
				<button type="submit" class="btn">Search</button>
			</div>
		</form>
		<div class="span7">
			<table class="table">
				<thead>
					<tr>
						<th>idx</th>
						<th>name</th>
						<th>description</th>
						<th>Action</th>
						<th>Authorities</th>
						<th>Add User</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (Organization organizationModel : list) {
					%>
					<tr>
						<%
							String idx = Long.toString(organizationModel.getIdx());
						%>
						<td><%=organizationModel.getIdx()%></td>
						<td><%=organizationModel.getName()%></td>
						<td><%=organizationModel.getDescription()%></td>
						<td>
							<div class="btn-group">
								<button class="btn btn-primary dropdown-toggle"
									data-toggle="dropdown">
									<i class="icon-pencil icon-white"></i>Action
								</button>
								<ul class="dropdown-menu">
									<li><a
										href="<%=request.getContextPath()%>/app/organization/update?idx=<%=idx%>">Update</a></li>
									<li><a
										href="<%=request.getContextPath()%>/app/organization/delete?idx=<%=idx%>">Delete</a></li>
								</ul>
							</div>
						</td>
						<td>
							<%
								String buttonToggle = "btn btn-primary";
									if (organizationIdx == Long.parseLong(idx)) {
										buttonToggle = "btn btn-primary disabled";
									}
							%> <a class="<%=buttonToggle%>" data-toggle="button"
							href="<%=request.getContextPath()%>/app/organization?organizationIdx=<%=idx%>">
								<i class="icon-chevron-right icon-white"></i>
						</a>
						</td>
						<td><a class="btn" data-toggle="modal"
							href="#myModal<%=idx%>"><i class="icon-pencil"></i></a>
							<div class="modal hide" id="myModal<%=idx%>">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">×</button>
									<h3>Add User</h3>
								</div>
								<form class="well form-search"
									action="<%=request.getContextPath()%>/app/organization/addUser"
									method="post">
									<div class="modal-body">
										User ID : <input type="text" name="id" class="form-vertical">
										<input type="hidden" class="form-vertical" name="organizationIdx"
											value="<%=idx%>">
									</div>
									<div class="modal-footer">
										<button class="btn btn-primary btn-large" type="submit">
											<i class="icon-pencil icon-white"></i>Add User
										</button>
									</div>
								</form>
							</div></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
		<div class="span4">
			<table class="table">
				<thead>
					<tr>
						<th>idx</th>
						<th>authority</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (Authorities authoritiesModel : authList) {
					%>
					<tr>
						<td><%=authoritiesModel.getIdx()%></td>
						<td><%=authoritiesModel.getAuthority()%></td>
						<td>
							<a class="btn btn-primary"
							href="<%=request.getContextPath()%>/app/organization/authDelete?idx=<%=authoritiesModel.getIdx()%>">
							<i class="icon-trash icon-white"></i>
							</a>
						</td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
			<div style="text-align: right">
				<a class="btn btn-primary"
					href="<%=request.getContextPath()%>/app/organization/authCreate?idx=<%=organizationIdx%>">
					<i class="icon-file icon-white"></i> Auth Create
				</a>
			</div>
		</div>
	</div>
	<%
		PagingNavigation pagingNavigation = new PagingNavigation();
		pagingNavigation.setCurrentPage(currentPage + 1);
		pagingNavigation.setPagePerBlock(5L);
		pagingNavigation.setRecordPerPage(20L);
		pagingNavigation.setTotalRecord(allCount);
		pagingNavigation.setHref("list");
		pagingNavigation.setParamters("");
		out.println(pagingNavigation.getHtml());
	%>

	<div style="text-align: right">
		<a class="btn btn-primary "
			href="<%=request.getContextPath()%>/app/organization/create"> <i
			class="icon-file icon-white"></i> Create
		</a>
	</div>

</body>
</html>