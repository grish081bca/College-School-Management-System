<%@ include file="fragments/header.jspf" %>
<div class="page-heading">
    <div><p class="eyebrow">Overview</p>
        <h1>Dashboard</h1></div>
</div>
<div class="cards"><a class="card" href="<c:url value='/web/tenants'/>">
    <span>Tenants</span><strong><c:out
        value="${tenantCount}"/></strong></a><a class="card"
                                                href="<c:url value='/web/countries'/>">
    <span>Countries</span><strong><c:out
        value="${countryCount}"/></strong></a><a class="card"
                                                 href="<c:url value='/web/states'/>"><span>States</span><strong><c:out
        value="${stateCount}"/></strong></a><a class="card"
                                               href="<c:url value='/web/cities'/>"><span>Cities</span><strong><c:out
        value="${cityCount}"/></strong></a></div>
<%@ include file="fragments/footer.jspf" %>
