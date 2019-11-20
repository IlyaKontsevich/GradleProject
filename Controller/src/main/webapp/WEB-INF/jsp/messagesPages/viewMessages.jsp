<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
Sort by: ${sort}
</br>
Filter by: ${filter}
<p align="right">user:  ${login}</p>
<p align="right"> <a href="/logout/">Log out</a></p>
</br>
<h1 align ="center">Messages</h1>
<table align ="center" border="2" width="70%" cellpadding="2">
    <tr><th><form  method="post" action="savefilterid${url}">
                <input name="filtervalue"  type="number" min="0" max="1000"/>
            </form></th>
        <th><form method="post" action="savefiltersenderUser${url}">
                <input name="filtervalue"  type="number" min="0" max="1000"/>
            </form></th>
        <th><form method="post" action="savefilterreceiverUser${url}">
                <input name="filtervalue"  type="number" min="0" max="1000"/>
            </form></th>
        <th><form method="post" action="savefiltermessage${url}">
                <input name="filtervalue"  type="text" size="50"/>
            </form></th>
        <th><form method="post" action="savefiltersendTime${url}">
                <input name="filtervalue"  type="date"/>
            <input type="submit" value="Save" />
            </form></th>
        <th><form method="post" action="savefilterisRead${url}">
                <select name="filtervalue" size="1">
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            <input type="submit" value="Save" />
        </form></th>
        <th><form method="post" action="savefilternul${url}">
                <input type="submit" value="Without filter" />
        </form></th>
        <th></th>
        <th></th>
    <tr><th>Id<a href="changesortid,asc${url}">&#8593</a><a href="changesortid,desc${url}">&#8595</a><a href="changesortid,nul${url}">&#215</a></th>
        <th>Sender id<a href="changesortsenderUser,asc${url}">&#8593</a><a href="changesortsenderUser,desc${url}">&#8595</a><a href="changesortsenderUser,nul${url}">&#215</a></th>
        <th>Receiver id<a href="changesortreceiverUser,asc${url}">&#8593</a><a href="changesortreceiverUser,desc${url}">&#8595</a><a href="changesortreceiverUser,nul${url}">&#215</a></th>
        <th>Text<a href="changesortmessage,asc${url}">&#8593</a><a href="changesort{message,desc}${url}">&#8595</a><a href="changesortmessage,nul${url}">&#215</a></th>
        <th>Send time<a href="changesortsendTime,asc${url}">&#8593</a><a href="changesortsendTime,desc${url}">&#8595</a><a href="changesortsendTime,nul${url}">&#215</a></th>
        <th>Is read<a href="changesortisRead,asc${url}">&#8593</a><a href="changesortisRead,desc${url}">&#8595</a><a href="changesortisRead,nul${url}">&#215</a></th>
        <th>Answer</th><th>Read</th><th>Delete</th>
        <c:forEach var="message" items="${list}">
    <tr>
        <td>${message.id}</td>
        <td>${message.senderUser.id} (${message.senderUser.name})</td>
        <td>${message.senderUser.id} (${message.receiverUser.name})</td>
        <td>${message.message}</td>
        <td>${message.sendTime}</td>
        <td>${message.isRead}</td>
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
<a href="pagesize5${url}">5</a>
<a href="pagesize10${url}">10</a>
<a href="pagesize15${url}">15</a>
<br/>
<p align="center"><a href="/user/redirect">View users</a></p>
