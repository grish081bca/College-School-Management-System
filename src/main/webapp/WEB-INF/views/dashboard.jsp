<%@ include file="fragments/header.jspf" %>
<%--<c:set var="pageEyebrow" value="Overview"/>--%>
<%--<c:set var="pageTitle" value="Dashboard"/>--%>
<%--<c:set var="pageDescription" value="Operational snapshot for tenant, location and access setup."/>--%>
<%--<%@ include file="fragments/page-header.jspf" %>--%>
<div class="cards">
    <a class="card" href="<c:url value='/web/tenants'/>"><span>Tenants</span><strong><c:out value="${tenantCount}"/></strong></a>
    <a class="card" href="<c:url value='/web/countries'/>"><span>Countries</span><strong><c:out value="${countryCount}"/></strong></a>
    <a class="card" href="<c:url value='/web/states'/>"><span>States</span><strong><c:out value="${stateCount}"/></strong></a>
    <a class="card" href="<c:url value='/web/cities'/>"><span>Cities</span><strong><c:out value="${cityCount}"/></strong></a>
</div>
<%@ include file="fragments/footer.jspf" %>
