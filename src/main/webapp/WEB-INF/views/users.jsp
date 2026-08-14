<%@ include file="fragments/header.jspf" %>
<section class="filter-panel">
    <form class="filter-grid" action="<c:url value='/web/users'/>" method="get">
        <label>Status
            <select name="status">
                <option value="">All statuses</option>
                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${status == selectedStatus ? 'selected' : ''}>${status}</option>
                </c:forEach>
            </select>
        </label>
        <c:set var="filterActionUrl" value="/web/users"/>
        <%@ include file="fragments/search-filter.jspf" %>
    </form>
</section>
<section class="panel">
    <div class="panel-header">
        <h2>Users</h2>
        <a class="primary" href="<c:url value='/web/users/add'/>">Add user</a>
    </div>
    <div class="table-wrap">
        <table class="data-table">
            <thead>
            <tr>
                <th>Username</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Tenant</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.objects}" var="user">
                <tr>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.phoneNumber}"/></td>
                    <td><c:out value="${user.tenantName}"/></td>
                    <td><c:out value="${user.status}"/></td>
                    <td class="actions-cell">
                        <a class="action-button secondary" href="<c:url value='/web/users/${user.id}/edit'/>" title="Edit user" aria-label="Edit user">
                            <i class="fa-solid fa-pen-to-square" aria-hidden="true"></i>
                            <span>Edit</span>
                        </a>
                        <a class="action-button secondary" href="<c:url value='/web/users/${user.id}'/>" title="View user" aria-label="View user">
                            <i class="fa-solid fa-eye" aria-hidden="true"></i>
                            <span>View</span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <%@ include file="fragments/data-table-empty.jspf" %>
    <%@ include file="fragments/pagination.jspf" %>
</section>
<%@ include file="fragments/footer.jspf" %>
