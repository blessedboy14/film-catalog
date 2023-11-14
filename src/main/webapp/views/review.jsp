<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="base/base.jsp"%>
<c:choose>
    <c:when test="${requestScope.error!=null}">
        <div class="alert alert-danger d-flex align-items-center" role="alert">
            <h6>${requestScope.error}</h6>
        </div>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${requestScope.review!=null}">
                <c:set var="review" value="${requestScope.review}"/>
                <div class="container mt-5">
                    <h3>Add Review</h3>
                    <form method="post">
                        <div class="form-group">
                            <label for="reviewText">Review Text</label>
                            <textarea class="form-control" id="reviewText" name="reviewText" rows="5" required>${review.getReviewText()}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="imdbRating" class="form-label mt-2">Rating</label>
                            <input type="number" class="form-control" id="imdbRating" value="${review.getReviewRating()}"
                                   name="imdbRating" step="0.1" min="0" max="10" required>
                        </div>
                        <p>Date Added: ${review.getDateAdded()}</p>
                        <button type="submit" class="btn btn-primary mt-3">Edit</button>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div class="container mt-5">
                <h3>Add Review</h3>
                <form method="post">
                    <div class="form-group">
                        <label for="reviewText">Review Text</label>
                        <textarea class="form-control" id="reviewText" name="reviewText" rows="5" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="imdbRating" class="form-label mt-2">Rating</label>
                        <input type="number" class="form-control" id="imdbRating" name="imdbRating" step="0.1" min="0" max="10" required>
                    </div>
                    <button type="submit" class="btn btn-primary mt-3">Add</button>
                </form>
                </div>
            </c:otherwise>
        </c:choose>
        <c:if test="${requestScope.errorBtn!=null}">
            <div class="alert alert-success d-flex align-items-center" role="alert">
                <h6>${requestScope.errorBtn}</h6>
            </div>
        </c:if>
    </c:otherwise>
</c:choose>
<style>
    textarea {
        resize: none;
    }
</style>
<%@include file="base/footer.jsp"%>
</body>
</html>
