<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
${error}
<div class="container" style="width: 300px;">
    <c:url value="/j_spring_security_check" var="loginUrl"/>
    <form action="${loginUrl}" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="form-control" name="j_username" placeholder="Email address" required autofocus
               value="admin@mail.ru">
        <input type="password" class="form-control" name="j_password" placeholder="Password" required value="1111">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
    </form>
    <td><a href="/registration"> Registration</a></td>
</div>