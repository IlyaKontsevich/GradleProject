<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<h1>Creat new Messages</h1>
<form:form method="post" action="save">
    <table >
        <tr>
            <td>Message : </td>
            <td><form:input path="message"  type="text" size="40"/></td>
        </tr>
        <tr>
            <td>Receiver email : </td>
            <td><form:select path="receiverEmail">
                <c:forEach items="${userList}" var="user">
                    <option value="${user.email}">${user.email}</option>
                </c:forEach>
            </form:select>
            </td>
        </tr>
        <tr>
            <td> </td>
            <td><input type="submit" value="Save" /></td>
        </tr>
    </table>
</form:form>
