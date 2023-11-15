<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="base/base.jsp"%>
<c:forEach var="user" items="${requestScope.users}">
    <c:choose>
        <c:when test="${sessionScope.loggedIn eq user.getEmail()}">

        </c:when>
        <c:otherwise>
            <div class="card mt-10" style="background-color: #ffafff;">
                <div class="card-body d-flex flex-column">
                    <div>
                        <h5 class="card-title">${user.getEmail()}</h5>
                        <form method="post" action="">
                            <input type="hidden" name="email" value="${user.getEmail()}">
                            <button type="submit">
                                <c:choose>
                                    <c:when test="${user.isBlocked()}">
                                        UNBLOCK
                                    </c:when>
                                    <c:otherwise>
                                        BLOCK
                                    </c:otherwise>
                                </c:choose>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</c:forEach>
<%@include file="base/footer.jsp"%>
