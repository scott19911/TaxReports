<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 15.02.2022
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link type= "text/css" rel="stylesheet" media="screen" href="css/sryle.css" />
<!------ Include the above in your HEAD tag ---------->

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>
                    Oops!</h1>
                <h2>
                    Something wrong</h2>
                <div class="error-details">
                    <h3>  ${errMessage}</h3>
                </div>
                <div class="error-actions">
                <c:if test='${!errMessage.equals("Sorry your need login or register")}'>
                    <a href="/reportList" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>
                        Take Me Home </a>
                </c:if>
                    <a href="/Login.jsp" class="btn btn-primary btn-lg">
                        <span class="glyphicon glyphicon-user"></span> Login </a>
                </div>
            </div>
        </div>
    </div>
</div>
