<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New User Registration</title>
</head>
<body bgcolor="Lightskyblue">  
<h1>Please fill the below Details</h1>
<div align="center">
        <form:form action="/jbigw/addUser" method="post" modelAttribute="userForm">
        <c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		
            <table border="1">
               
                <tr>
                    <td>User Name:</td>
                    <td><form:input path="userName" /></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:password path="password" /></td>
                </tr>
                <tr>
                    <td>First Name</td>
                    <td><form:input path="firstName" /></td>
                </tr>
                <tr>
                    <td>Last Name</td>
                    <td><form:input path="lastName" /></td>
                </tr>
                <tr>
                    <td>Mobile Number</td>
                    <td><form:input path="mobileNumber" /></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Register" /></td>
                </tr>
            </table>
        </form:form>
    </div>
</body>
</body>
</html>