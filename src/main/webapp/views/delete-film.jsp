
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base/base.jsp"%>
<div style="display: flex; align-items: center; justify-content: center; height:100%;flex-direction: column;">
    <h2>Are you really want to delete film?</h2>
    <form method="post" action="delete">
        <input type="hidden" name="id" value="${requestScope.film_id}">
        <div style="display: flex; justify-content: flex-end; margin-top: auto;">
            <button style="margin-right: 10px;" type="submit" name="confirm">Confirm</button>
            <button type="submit" name="decline">Cancel</button>
        </div>
    </form>
</div>
<%@include file="base/footer.jsp"%>
</body>
</html>
