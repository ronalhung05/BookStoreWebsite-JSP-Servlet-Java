<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="page_head.jsp">
    <jsp:param name="pageTitle" value="Register as a Customer"/>
</jsp:include>
<body>
<div class="container">
    <jsp:directive.include file="header.jsp"/>

    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col text-center"><h2>Register as a Customer</h2></div>
    </div>
    <div class="row">&nbsp;</div>

    <form action="register_customer" method="post" style="max-width: 800px; margin: 0 auto;">
        <jsp:directive.include file="../common/customer_form.jsp"/>
    </form>

    <jsp:directive.include file="footer.jsp"/>
</div>
</body>
<script type="text/javascript" src="js/customer-form.js"></script>
</html>