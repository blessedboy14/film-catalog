<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="base/base.jsp"%>
<div class="container">
    <c:set var="film" value="${requestScope.film}"/>
    <c:choose>
        <c:when test="${film!= null}">
            <h1><fmt:message key="detail.title"/></h1>
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${film.getTitle()}</h5>
                    <p class="card-text"><strong>Director: </strong>${film.getDirector().toString()}</p>
                    <p class="card-text"><strong>Release Year: </strong>${film.getReleaseYear()}</p>
                    <p class="card-text"><strong>Duration: </strong>${film.getDurationInMinutes()} min</p>
                    <p class="card-text"><strong>Genre: </strong>${film.getFilmGenres(1)}</p>
                    <p class="card-text"><strong>Rating: </strong>${film.getIMDbRating()}/10</p>
                    <p class="card-text"><strong>Age Limit: </strong>${film.getAgeLimit()}</p>
                    <p class="card-text"><strong>Film stars: </strong>${film.getStars()}</p>
                </div>
            </div>
            <a href="/film/${requestScope.url}/review" class="btn btn-secondary mt-3"><fmt:message key="detail.review"/></a>
        </c:when>
        <c:otherwise>
            <h2>Film not found. Sorry<3</h2>
        </c:otherwise>
    </c:choose>
    <a href="/films" class="btn btn-primary mt-3"><fmt:message key="detail.back"/></a>
</div>
<div class="container mt-4">
    <c:forEach items="${requestScope.reviews}" var="review">
        <div class="card">
            <div class="card-body">
                <h6 class="card-title">${review.getReviewer()}</h6>
                <p class="card-text">${review.getReviewText()}</p>
                <div class="d-flex justify-content-between">
                    <span class="badge badge-primary text-dark" style="font-size: 14px;">Rating: ${review.getReviewRating()}</span>
                    <span class="badge badge-secondary text-dark" style="font-size: 14px;">${review.getPrettyDate()}</span>
                </div>
            </div>
        </div>
        <br>
    </c:forEach>
</div>
<%@include file="base/footer.jsp"%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
