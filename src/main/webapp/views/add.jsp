<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base/base.jsp"%>
    <h1 class="my-4 text-center"><fmt:message key="add.title"/></h1>
    <div class="d-flex align-items-center justify-content-center">
        <form method="post">
            <div class="form-group col-md-6">
                <label for="inputTitle">Enter title</label>
                <input type="text" name="title" class="form-control" id="inputTitle" aria-describedby="titleHelp" placeholder="Enter title" required>
            </div>
            <div class="form-group col-md-6 mt-2">
                <label for="inputFName">Director first Name</label>
                <input type="text" class="form-control" id="inputFName" name="firstName" placeholder="First Name" required>
            </div>
            <div class="form-group col-md-6 mt-2">
                <label for="inputSName">Director second Name</label>
                <input type="text" class="form-control" id="inputSName" name="secondName" placeholder="Second Name" required>
            </div>
            <div class="row">
                <div class="form-group col-md-6 mt-2">
                    <label for="genresSelect">Select genres</label>
                    <select multiple name="genres[]" class="form-control" id="genresSelect" required>
                        <c:forEach var="genre" items="${requestScope.genres}">
                            <option value="${genre}">${genre.getName()}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-6 mt-2">
                    <label for="startArea">Enter film stars</label>
                    <textarea class="form-control" name="stars" id="startArea" rows="4" required></textarea>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="releaseYear" class="form-label">Release Year</label>
                    <input type="number" class="form-control" id="releaseYear" name="releaseYear" placeholder="Enter year" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="imdbRating" class="form-label">IMDb Rating</label>
                    <input type="number" class="form-control" id="imdbRating" name="imdbRating" step="0.1" min="0" max="10" required>
                </div>
                <div class="form-group col-md-6 mt-3">
                    <label for="ageLimit" class="form-label">Age Limit(A, U, UA, R, NA, 0, G, PG, PG-13)</label>
                    <input type="text" class="form-control" id="ageLimit" name="ageLimit" placeholder="Enter limit" required>
                </div>
                <div class="form-group col-md-6 mt-3">
                    <label for="duration" class="form-label">Duration</label>
                    <input type="number" class="form-control" id="duration" name="duration" placeholder="Enter duration" required>
                </div>
            </div>
            <c:set var="param" value="${param.name}" />
            <c:choose>
                <c:when test="${requestScope.errorFilm=='added'}">
                    <div class="alert alert-success" role="alert">
                        <h6>Film successfully added</h6>
                    </div>
                </c:when>
                <c:when test="${requestScope.errorFilm!=null}">
                    <div class="alert alert-danger" role="alert">
                        <h6>${requestScope.errorFilm}</h6>
                    </div>
                </c:when>
            </c:choose>
            <button type="submit" class="btn btn-primary mt-4">Submit</button>
        </form>
    </div>

    <style>
        textarea {
            resize: none;
        }
    </style>
    <%@include file="base/footer.jsp"%>
</body>
</html>
