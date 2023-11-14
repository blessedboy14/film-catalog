<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="base/base.jsp"%>
<h1><fmt:message key="sign.title"/></h1>
<form method="post">
    <div class="form-group">
        <label for="emailInput">Email address</label>
        <input type="email" name="email" class="form-control" id="emailInput" placeholder="Enter email" required>
    </div>
    <div class="form-group">
        <label for="passInput">Password</label>
        <input type="password" name="pass" class="form-control" id="passInput" size="20" placeholder="Password" required>
    </div>
    <div class="form-check">
        <input type="checkbox" class="form-check-input" id="checkBox" required>
        <small id="checkHelp" class="text-muted">I'm not a robot</small>
    </div>
    <c:choose>
        <c:when test="${requestScope.error!=null}">
            <div class="alert alert-danger" role="alert">
                <h6>${requestScope.error}</h6>
            </div>
        </c:when>
    </c:choose>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
<%@include file="base/footer.jsp"%>
</body>
</html>