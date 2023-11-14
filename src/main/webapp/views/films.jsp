<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base/base.jsp"%>
<div class="container">
    <div class="row">
        <h1 class="col-my-4 text-center"><fmt:message key="films.title"/></h1>
        <div class="search-form">
            <form class="d-flex" method="post">
                <input class="form-control me-1" name="searchTitle" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-primary" type="submit">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </form>
        </div>
    </div>
    <div class="row">
        <c:choose>
            <c:when test="${requestScope.error==null}">
                <c:forEach var="film" items="${requestScope.films}">
                    <div class="col-lg-4 col-md-6 mb-4">
                        <div class="card h-100" style="background-color: #ffafff;">
                            <div class="card-body d-flex flex-column">
                                <div>
                                    <h5 class="card-title">${film.getTitle()}</h5>
                                    <p class="card-text"><strong>Director:</strong> ${film.getDirector()}</p>
                                    <p class="card-text"><strong>Genres:</strong> ${film.getFilmGenres(1)}</p>
                                    <p class="card-text"><strong>Rating:</strong> ${film.getIMDbRating()}</p>
                                </div>
                                <div style="display: flex; justify-content: flex-end; margin-top: auto;">
                                    <c:if test="${sessionScope.adminStatus!=null}">
                                        <a style="margin-right: 10px;" href="/film/${film.getId()}/delete" class="btn btn-warning mt-auto align-self-end"><fmt:message key="films.delete"/></a>
                                    </c:if>
                                    <a href="/film/${film.getId()}" class="btn btn-primary mt-auto align-self-end"><fmt:message key="films.more"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="text-end">
                    <h2>There's no films yet!!</h2>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<style>
    .card-text {
        display: block;
        margin-bottom: 0.5rem;
    }
     .search-form {
         display: flex;
         justify-content: flex-end;
         align-items: center;
     }
    .search-input {
        width: 300px;
    }
</style>
<%@include file="base/footer.jsp"%>
</body>
</html>
