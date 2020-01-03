<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
Sort by: ${sort}
</br>
Filter by: ${filter}
<p align="right">user: ${login}</p>
<p align="right"><a href="${userId}/messages/">(Unread messages: ${unreadMessages})</a></p>
<p align="right"><a href="/logout/">Log out</a></p>
</br>
<h1 align="center">Users List</h1>
<table align="center" border="2" width="70%" cellpadding="2">
    <tr>
        <th>
            <form method="post" action="savefilterid${url}">
                <input name="filtervalue" type="number" min="0" max="1000"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefiltername${url}">
                <input name="filtervalue" type="text" size="10"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefilterage${url}">
                <input name="filtervalue" type="number" min="1" max="99"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefilteremail${url}">
                <input name="filtervalue" type="email" size="10"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefilternul${url}">
                <input type="submit" value="Without filter"/>
            </form>
        </th>
        <th></th>
        <th></th>
        <th></th>
    <tr>
        <th>Id<a href="changesortid,asc${url}">&#8593</a><a href="changesortid,desc${url}">&#8595</a><a
                href="changesortid,nul${url}">&#215</a></th>
        <th>Name<a href="changesortname,asc${url}">&#8593</a><a href="changesortname,desc${url}">&#8595</a><a
                href="changesortname,nul${url}">&#215</a></th>
        <th>Age<a href="changesortage,asc${url}">&#8593</a><a href="changesortage,desc${url}">&#8595</a><a
                href="changesortage,nul${url}">&#215</a></th>
        <th>Email<a href="changesortemail,asc${url}">&#8593</a><a href="changesortemail,desc${url}">&#8595</a><a
                href="changesortemail,nul${url}">&#215</a></th>
        <th>View users tasks</th>
        <th>Edit</th>
        <th>Delete</th>
        <th>View users messages</th>
        <c:forEach var="user" items="${list}">
    <tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td><a href="${user.id}/viewtask">View task</a></td>
        <td><a href="${user.id}/edit">Edit</a></td>
        </form>
        <form:form method="DELETE" action="${user.id}">
            <td><input type="submit" value="Delete"/></td>
        </form:form>
        <td><a href="${user.id}/viewMessage">View messages</a></td>
    </tr>
    </c:forEach>
</table>
<br/>
<p align="center">page: ${position} of ${Math.round(Math.ceil(size/pageSize))}</p>
<c:if test="${position < Math.round(Math.ceil(size/pageSize))}">
    <a href="changepage${position+1}${url}">Next</a>
</c:if>
<c:if test="${position > 1}">
    <a href="changepage${position-1}${url}">Back</a>
</c:if>
<br/>
<p align="center"><a href="form">Add New User</a></p>
Change page size
<a href="pagesize5${url}">5</a>
<a href="pagesize10${url}">10</a>
<a href="pagesize15${url}">15</a>
<p align="right"><a href="/save/pdf" target="_blank">Save info to PDF file</a></p>
<p align="right"><a href="/save/txt" target="_blank">Save info to TXT file</a></p>
<p align="right"><a href="/save/csv" target="_blank">Save info to CSV file</a></p>
<p align="right"><a href="/save/xlsx" target="_blank">Save info to XLSX file</a></p>
<br/>