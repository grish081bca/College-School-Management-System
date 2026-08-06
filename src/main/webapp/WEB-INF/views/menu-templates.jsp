<%@ include file="fragments/header.jspf" %>
<%--<c:set var="pageEyebrow" value="Access administration"/>--%>
<%--<c:set var="pageTitle" value="Menu templates"/>--%>
<%--<c:set var="pageDescription" value="Assign active menus to user types globally or per tenant."/>--%>
<%--<c:set var="pageActionUrl" value="/web/menu-templates/add"/>--%>
<%--<c:set var="pageActionText" value="Add menu template"/>--%>
<%--<%@ include file="fragments/page-header.jspf" %>--%>
<section class="filter-panel">
    <form class="filter-grid" action="<c:url value='/web/menu-templates'/>" method="get">
        <label>User type
            <select name="userType">
                <option value="">All user types</option>
                <c:forEach items="${userTypes}" var="type">
                    <option value="${type}" ${type == selectedUserType ? 'selected' : ''}>${type}</option>
                </c:forEach>
            </select>
        </label>
        <label>Status
            <select name="status">
                <option value="">All statuses</option>
                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${status == selectedStatus ? 'selected' : ''}>${status}</option>
                </c:forEach>
            </select>
        </label>
        <c:set var="filterActionUrl" value="/web/menu-templates"/>
        <%@ include file="fragments/search-filter.jspf" %>
    </form>
</section>
<section class="panel">
    <div class="table-wrap">
        <table class="data-table">
            <thead><tr><th>Template</th><th>User type</th><th>Menus</th><th>Status</th><th>Change status</th></tr></thead>
                        <tbody><c:forEach items="${page.objects}" var="template">
                            <tr>
                                <td><c:out value="${template.name}"/></td>
                                <td><c:out value="${template.userType}"/></td>
                                <td><c:out value="${template.menuName}"/></td>
                                <td><c:out value="${template.status}"/></td>
                    <td>
                        <form class="inline" action="<c:url value='/web/menu-templates/${template.id}/status'/>" method="post">
                            <select name="status"><c:forEach items="${statuses}" var="status"><option value="${status}" ${status == template.status ? 'selected' : ''}>${status}</option></c:forEach></select>
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
