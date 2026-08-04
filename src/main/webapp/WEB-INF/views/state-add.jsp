<%@ include file="fragments/header.jspf" %>
<%--<c:set var="pageEyebrow" value="Location setup"/>--%>
<%--<c:set var="pageTitle" value="Add state"/>--%>
<%--<c:set var="pageDescription" value="Create a state or province under a country."/>--%>
<%--<c:set var="pageActionUrl" value="/web/states"/>--%>
<%--<c:set var="pageActionText" value="List states"/>--%>
<%--<%@ include file="fragments/page-header.jspf" %>--%>
<section class="form-panel">
    <form class="form-grid enterprise-form" action="<c:url value='/web/states'/>" method="post">
        <label>Name<input name="name" required maxlength="100"></label>
        <label>Country<select name="countryId" required>
            <option value="">Select country</option>
            <c:forEach items="${countries}" var="country">
                <option value="${country.id}"><c:out value="${country.name}"/></option>
            </c:forEach>
        </select></label>
        <div class="form-actions">
            <button class="primary" type="submit">Add state</button>
            <a class="button secondary" href="<c:url value='/web/states'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>
