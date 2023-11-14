<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base.jsp"%>
<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h1 class="display-1 fw-bold">404</h1>
        <p class="fs-3"> <span class="text-danger">Opps!</span> <fmt:message key="error.not_found"/></p>
        <p class="lead">
            <fmt:message key="error.text"/>
        </p>
        <a href="/" class="btn btn-primary"><fmt:message key="error.home"/></a>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
