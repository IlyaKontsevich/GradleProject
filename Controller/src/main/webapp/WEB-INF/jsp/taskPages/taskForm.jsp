<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<h1>Add New Task</h1>
Priority:
<form:form method="post" action="save">
    <table>
        <tr>
            <td>Name :</td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <form:select path="priority">
                <form:option value="low"/>
                <form:option value="medium"/>
                <form:option value="high"/>
            </form:select>
        </tr>
        <tr>
            <td>Deadline :</td>
            <td><input name="date" type="date" value="2019-12-12"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Save"/></td>
        </tr>
    </table>
</form:form>

