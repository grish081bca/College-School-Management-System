<%@ include file="fragments/header.jspf" %>
<c:set var="pageEyebrow" value="Location setup"/>
<c:set var="pageTitle" value="Add city"/>
<c:set var="pageDescription" value="Create a city and map it to a state."/>
<c:set var="pageActionUrl" value="/web/cities"/>
<c:set var="pageActionText" value="List cities"/>
<%@ include file="fragments/page-header.jspf" %>
<section class="form-panel">
    <form class="form-grid enterprise-form" action="<c:url value='/web/cities'/>" method="post">
        <label>Name<input name="name" required maxlength="100"></label>
        <label>State<select name="stateId" required>
            <option value="">Select state</option>
            <c:forEach items="${states}" var="state">
                <option value="${state.id}"><c:out value="${state.name}"/> - <c:out value="${state.countryName}"/></option>
            </c:forEach>
        </select></label>
        <div class="form-actions">
            <button class="primary" type="submit">Add city</button>
            <a class="button secondary" href="<c:url value='/web/cities'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>
