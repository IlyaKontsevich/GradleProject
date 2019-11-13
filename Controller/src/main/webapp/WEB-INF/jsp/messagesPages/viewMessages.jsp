<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<p align="right">user:  ${login}</p>
<p align="right"> <a href="/logout/">Log out</a></p>
</br>
<h1 align ="center">Messages</h1>
<table align ="center" border="2" width="70%" cellpadding="2">
    <tr><th><form  method="post" action="savefilter{id}${url}">
                <input name="filtervalue"  type="number" min="0" max="1000"/>
            </form></th>
        <th><form method="post" action="savefilter{senderId}${url}">
                <input name="filtervalue"  type="number" min="0" max="1000"/>
            </form></th>
        <th><form method="post" action="savefilter{receiverId}${url}">
                <input name="filtervalue"  type="number" min="0" max="1000"/>
            </form></th>
        <th><form method="post" action="savefilter{message}${url}">
                <input name="filtervalue"  type="text" size="50"/>
            </form></th>
        <th><form method="post" action="savefilter{sendTime}${url}">
                <input name="filtervalue"  type="date"/>
            </form></th>
        <th><form method="post" action="savefilter{isRead}${url}">
                <select name="filtervalue" size="1">
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
        </form></th>
        <th><form method="post" action="savefilter{nul}${url}">
                <input type="submit" value="Without filter" />
        </form></th>
        <th></th>
        <th></th>
    <tr><th>Id<a href="changesort{id,asc}${url}">&#8593</a><a href="changesort{id,desc}${url}">&#8595</a><a href="changesort{id,nul}${url}">&#215</a></th>
        <th>Sender id<a href="changesort{senderUser,asc}${url}">&#8593</a><a href="changesort{senderUser,desc}${url}">&#8595</a><a href="changesort{senderUser,nul}${url}">&#215</a></th>
        <th>Receiver id<a href="changesort{receiverUser,asc}${url}">&#8593</a><a href="changesort{receiverUser,desc}${url}">&#8595</a><a href="changesort{receiverUser,nul}${url}">&#215</a></th>
        <th>Text<a href="changesort{message,asc}${url}">&#8593</a><a href="changesort{message,desc}${url}">&#8595</a><a href="changesort{message,nul}${url}">&#215</a></th>
        <th>Send time<a href="changesort{sendTime,asc}${url}">&#8593</a><a href="changesort{sendTime,desc}${url}">&#8595</a><a href="changesort{sendTime,nul}${url}">&#215</a></th>
        <th>Is read<a href="changesort{isRead,asc}${url}">&#8593</a><a href="changesort{isRead,desc}${url}">&#8595</a><a href="changesort{isRead,nul}${url}">&#215</a></th>
        <th>Answer</th><th>Read</th><th>Delete</th>
        <c:forEach var="message" items="${list}">
    <tr>
        <td>${message.id}</td>
        <td>${message.senderUser.id} (${message.senderUser.name})</td>
        <td>${message.senderUser.id} (${message.receiverUser.name})</td>
        <td>${message.message}</td>
        <td>${message.sendTime}</td>
        <td>${message.read}</td>
        <td><a href="${message.id}/answer">Answer</a></td>
        <td><a href="${message.id}/read">Read</a></td>
        </form>
        <form:form method="DELETE" action="${message.id}">
            <td><input type="submit" value="Delete" /></td>
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
<br/>
<p align="center"><a href="form">Add New Messages</a></p>
Change page size
<a href="pagesize{5}${url}">5</a>
<a href="pagesize{10}${url}">10</a>
<a href="pagesize{15}${url}">15</a>
<br/>
<p align="center"><a href="/user/redirect">View users</a></p>
