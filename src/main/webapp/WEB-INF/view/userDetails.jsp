<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registered Details</title>
</head>
<body>
    <div align="center">
		
        <table border="0">
             <tr>
                <td>User Name:</td>
                <td>${userForm.userName}</td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td>${userForm.firstName}</td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td>${userForm.lastName}</td>
            </tr>
            <tr>
                <td>Mobile Number</td>
                <td>${userForm.mobileNumber}</td>
            </tr>
 
        </table>
    </div>
</body>
</html>