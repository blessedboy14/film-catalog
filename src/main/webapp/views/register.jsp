<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="base/base.jsp"%>
    <h1><fmt:message key="reg.title"/></h1>
    <form method="post">
        <div class="form-group">
            <label for="emailInput">Email address</label>
            <input type="email" name="email" class="form-control" id="emailInput" placeholder="Enter email" required>
        </div>
        <div class="form-group">
            <label for="passInput">Password</label>
            <input type="password" name="pass" class="form-control" id="passInput" size="20" placeholder="Password" required>
        </div>
        <div class="form-group">
            <label for="phoneNumber">Phone Number</label>
            <select name="countryCode" id="countryCode" onchange="updatePhoneNumberFormat()">
                <option value="BY">Belarus (+375)</option>
                <option value="RU">Russia (+7)</option>
            </select>
            <input type="text" name="phoneNumber" id="phoneNumber" size="11" placeholder="Enter phone number" required>
        </div>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="checkBox" required>
            <small id="checkHelp" class="text-muted">Agree with privacy policy</small>
        </div>
        <c:choose>
            <c:when test="${requestScope.error!=null}">
                <div class="alert alert-danger d-flex align-items-center" role="alert">
                    <h6>${requestScope.error}</h6>
                </div>
            </c:when>
        </c:choose>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <%@include file="base/footer.jsp"%>
</body>
</html>

<script>
    function updatePhoneNumberFormat() {
        var countryCode = document.getElementById("countryCode").value;
        var phoneNumberInput = document.getElementById("phoneNumber");
        phoneNumberInput.value = "";
        if (countryCode === "BY") {
            phoneNumberInput.placeholder = "e.g., +375 XX XXX-XX-XX";
            phoneNumberInput.pattern = "\\+375 \\d{2} \\d{3}-\\d{2}-\\d{2}";
        } else if (countryCode === "RU") {
            phoneNumberInput.placeholder = "e.g., +7 XXX XXX-XX-XX";
            phoneNumberInput.pattern = "\\+7 \\d{3} \\d{3}-\\d{2}-\\d{2}";
        }
    }
</script>