<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<header class="p-3 bg-dark text-white">
    <fmt:setLocale value="${requestScope.lang}"/>
    <fmt:setBundle basename="locale"/>
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="/" class="nav-link px-2 text-white"><fmt:message key="navbar.home"/></a></li>
                <li><a id="redirectLinkFilms" href="#" class="nav-link px-2 text-white"><fmt:message key="navbar.films"/></a></li>
                <li><a id="redirectLinkAbout" href="#" class="nav-link px-2 text-white"><fmt:message key="navbar.about"/></a></li>
                <c:if test="${sessionScope.adminStatus!=null}">
                    <li><a id="redirectLinkAdd" href="#" class="nav-link px-2 text-white"><fmt:message key="navbar.add_film"/></a></li>
                </c:if>
            </ul>
            <c:choose>
                <c:when test="${sessionScope.loggedIn!=null}">
                    <div class="text-end">
                        <button type="button" class="btn btn-outline-light me-2" onclick="redirectToProfile()"><fmt:message key="navbar.profile"/></button>
                        <form action="logout" method="POST" class="d-inline">
                            <button type="submit" class="btn btn-warning"><fmt:message key="navbar.log_out"/></button>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-end">
                        <button type="button" class="btn btn-outline-light me-2" onclick="redirectToSignIn()"><fmt:message key="navbar.sign_in"/></button>
                        <button type="button" class="btn btn-warning" onclick="redirectToReg()"><fmt:message key="navbar.register"/></button>
                    </div>
                </c:otherwise>
            </c:choose>
            <div style="display: flex; justify-content: flex-end; align-items:center; margin-left: 10px;">
                <form method="post" action="/locale" style="margin: 0;">
                    <select name="language" onchange="this.form.submit()">
                        <option value="en" <c:if test="${requestScope.lang=='en'}">selected</c:if>>English</option>
                        <option value="ru" <c:if test="${requestScope.lang=='ru'}">selected</c:if>>Русский</option>
                    </select>
                </form>
            </div>
        </div>
    </div>
</header>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var link = document.getElementById("redirectLinkFilms");
        link.addEventListener("click", function(event) {
            event.preventDefault(); // Prevent default hyperlink behavior
            window.location.href = "/films";
        });
    });
    document.addEventListener("DOMContentLoaded", function() {
        var link = document.getElementById("redirectLinkAbout");
        link.addEventListener("click", function(event) {
            event.preventDefault(); // Prevent default hyperlink behavior
            window.location.href = "/about";
        });
    });
    document.addEventListener("DOMContentLoaded", function() {
        var link = document.getElementById("redirectLinkAdd");
        link.addEventListener("click", function(event) {
            event.preventDefault(); // Prevent default hyperlink behavior
            window.location.href = "/add-film";
        });
    });
    function redirectToSignIn() {
        window.location.href = "/signin";
    }

    function redirectToProfile(){
        window.location.href = "/profile";
    }

    function redirectToIndex(){
        window.location.href = "/";
    }

    function redirectToReg() {
        window.location.href = "/register";
    }
</script>