<%@ include file="fragments/header.jspf" %>
<%--<c:set var="pageEyebrow" value="Tenant administration"/>--%>
<%--<c:set var="pageTitle" value="Tenants"/>--%>
<%--<c:set var="pageDescription" value="Manage tenant colleges, contact information, location and operational status."/>--%>
<%--<c:set var="pageActionUrl" value="/web/tenants/add"/>--%>
<%--<c:set var="pageActionText" value="Add tenant"/>--%>
<%--<%@ include file="fragments/page-header.jspf" %>--%>
<section class="filter-panel">
    <form class="filter-grid" action="<c:url value='/web/tenants'/>" method="get">
        <label>Status
            <select name="status">
                <option value="">All statuses</option>
                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${status == selectedStatus ? 'selected' : ''}>${status}</option>
                </c:forEach>
            </select>
        </label>
        <c:set var="filterActionUrl" value="/web/tenants"/>
        <%@ include file="fragments/search-filter.jspf" %>
    </form>
</section>
<section class="panel">
    <div class="table-wrap">
        <table class="data-table">
            <thead><tr><th>Code</th><th>Name</th><th>Contact</th><th>Location</th><th>Status</th><th>Change status</th></tr></thead>
            <tbody><c:forEach items="${page.objects}" var="tenant">
                <tr>
                    <td><c:out value="${tenant.tenantCode}"/></td>
                    <td><c:out value="${tenant.tenantName}"/></td>
                    <td><c:out value="${tenant.contactEmail}"/><br><c:out value="${tenant.contactPhone}"/></td>
                    <td><c:out value="${tenant.cityName}"/>, <c:out value="${tenant.stateName}"/></td>
                    <td><c:out value="${tenant.status}"/></td>
                    <td>
                        <form class="inline" action="<c:url value='/web/tenants/${tenant.id}/status'/>" method="post">
                            <select name="status"><c:forEach items="${statuses}" var="status"><option value="${status}" ${status == tenant.status ? 'selected' : ''}>${status}</option></c:forEach></select>
                            <button class="secondary" type="submit">Save</button>
                        </form>
                    </td>
                </tr>
            </c:forEach></tbody>
        </table>
    </div>
    <%@ include file="fragments/data-table-empty.jspf" %>
    <%@ include file="fragments/pagination.jspf" %>
</section>
<%@ include file="fragments/footer.jspf" %>
