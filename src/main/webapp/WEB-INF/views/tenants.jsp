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
            <thead>
            <tr>
                <th>Code</th>
                <th>Name</th>
                <th>Email</th>
                <th>Secondary Email</th>
                <th>Phone</th>
                <th>Secondary Phone</th>
                <th>Address Line 1</th>
                <th>Address Line 2</th>
                <th>Country</th>
                <th>State</th>
                <th>City</th>
                <th>Postal Code</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody><c:forEach items="${page.objects}" var="tenant">
                <tr>
                    <td><c:out value="${tenant.tenantCode}"/></td>
                    <td><c:out value="${tenant.tenantName}"/></td>
                    <td><c:out value="${tenant.contactEmail}"/></td>
                    <td><c:out value="${tenant.contactEmailSecondary}"/></td>
                    <td><c:out value="${tenant.contactPhone}"/></td>
                    <td><c:out value="${tenant.contactPhoneSecondary}"/></td>
                    <td><c:out value="${tenant.addressLine1}"/></td>
                    <td><c:out value="${tenant.addressLine2}"/></td>
                    <td><c:out value="${tenant.countryName}"/></td>
                    <td><c:out value="${tenant.stateName}"/></td>
                    <td><c:out value="${tenant.cityName}"/></td>
                    <td><c:out value="${tenant.postalCode}"/></td>
                    <td><c:out value="${tenant.status}"/></td>
                    <td class="actions-cell">
                        <a class="action-button secondary" href="<c:url value='/web/tenants/${tenant.id}/edit'/>" title="Edit tenant" aria-label="Edit tenant">
                            <i class="fa-solid fa-pen-to-square" aria-hidden="true"></i>
                            <span>Edit</span>
                        </a>
                        <a class="action-button secondary" href="<c:url value='/web/tenants/${tenant.id}'/>" title="View tenant" aria-label="View tenant">
                            <i class="fa-solid fa-eye" aria-hidden="true"></i>
                            <span>View</span>
                        </a>
                        <form class="inline" action="<c:url value='/web/tenants/${tenant.id}/status'/>" method="post">
                            <input type="hidden" name="status" value="${tenant.status == 'ACTIVE' ? 'INACTIVE' : 'ACTIVE'}">
                            <button class="action-button secondary" type="submit" title="Change tenant status" aria-label="Change tenant status">
                                <i class="fa-solid fa-toggle-on" aria-hidden="true"></i>
                                <span>Change status</span>
                            </button>
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
