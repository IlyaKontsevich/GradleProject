<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
Sort by: ${sort}
</br>
Filter by: ${filter}
<p align="right">user: ${login}</p>
<p align="right"><a href="/logout/">Log out</a></p>
<h1 align="center">Tasks List</h1>
<table align="center" border="2" width="70%" cellpadding="2">
    <tr>
        <th>
            <form method="post" action="savefilterid${url}">
                <input name="filtervalue" type="number" min="0" max="1000"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefilterpriority${url}">
                <select name="filtervalue" size="1">
                    <option value="low">low</option>
                    <option value="medium">medium</option>
                    <option value="high">high</option>
                </select>
                <input type="submit" value="Save"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefiltername${url}">
                <input name="filtervalue" type="text" size="10"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefilterisDone${url}">
                <select name="filtervalue" size="1">
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
                <input type="submit" value="Save"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefilterdeadline${url}">
                <input name="filtervalue" type="date"/>
                <input type="submit" value="Save"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefiltertimeaAdd${url}">
                <input name="filtervalue" type="date"/>
                <input type="submit" value="Save"/>
            </form>
        </th>
        <th>
            <form method="post" action="savefilternul${url}">
                <input type="submit" value="Without filter"/>
            </form>
        </th>
        <th></th>
    <tr>
        <th>Id<a href="changesortid,asc${url}">&#8593</a><a href="changesortid,desc${url}">&#8595</a><a
                href="changesortid,nul${url}">&#215</a></th>
        <th>Priority<a href="changesortpriority,asc${url}">&#8593</a><a
                href="changesortpriority,desc${url}">&#8595</a><a href="changesortpriority,nul${url}">&#215</a></th>
        <th>Name<a href="changesortname,asc${url}">&#8593</a><a href="changesortname,desc${url}">&#8595</a><a
                href="changesortname,nul${url}">&#215</a></th>
        <th>Status<a href="changesortisdone,asc${url}">&#8593</a><a href="changesortisdone,desc${url}">&#8595</a><a
                href="changesortisdone,nul${url}">&#215</a></th>
        <th>Deadline<a href="changesortdeadline,asc${url}">&#8593</a><a
                href="changesortdeadline,desc${url}">&#8595</a><a href="changesortdeadline,nul${url}">&#215</a></th>
        <th>Date of Add<a href="changesorttimeadd,asc${url}">&#8593</a><a href="changesorttimeadd,desc${url}">&#8595</a><a
                href="changesorttimeadd,nul${url}">&#215</a></th>
        <th>Edit</th>
        <th>Delete</th>
        <c:forEach var="task" items="${list}">
    <tr>
        <td>${task.id}</td>
        <td>${task.priority}</td>
        <td>${task.name}</td>
        <td>${task.isDone}</td>
        <td>${task.deadLine}</td>
        <td>${task.timeAdd}</td>
        <td><a href="${task.id}/edit">Edit</a></td>
        <form:form method="DELETE" action="${task.id}">
            <td><input type="submit" value="Delete"/></td>
        </form:form>
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

<p align="center"><a href="form">Add New Task</a></p>
Change page size
<a href="pagesize5${url}">5</a>
<a href="pagesize10${url}">10</a>
<a href="pagesize15${url}">15</a>
</br>
<p align="center"><a href="/user/redirect">View users</a></p>
