<%@ include file="fragments/header.jspf" %>
<%--<c:set var="pageEyebrow" value="Access administration"/>--%>
<%--<c:set var="pageTitle" value="Menus"/>--%>
<%--<c:set var="pageDescription" value="Manage dynamic super menus and sub-menus shown in the sidebar."/>--%>
<%--<c:set var="pageActionUrl" value="/web/menus/add"/>--%>
<%--<c:set var="pageActionText" value="Add menu"/>--%>
<%--<%@ include file="fragments/page-header.jspf" %>--%>
<section class="filter-panel">
    <form class="filter-grid" action="<c:url value='/web/menus'/>" method="get">
        <label>Type
            <select name="menuType">
                <option value="">All types</option>
                <c:forEach items="${menuTypes}" var="type">
                    <option value="${type}" ${type == selectedMenuType ? 'selected' : ''}>${type}</option>
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
        <c:set var="filterActionUrl" value="/web/menus"/>
        <%@ include file="fragments/search-filter.jspf" %>
    </form>
</section>
<section class="panel">
    <div class="table-wrap">
        <table class="data-table">
            <thead><tr><th>Code</th><th>Name</th><th>URL</th><th>Parent</th><th>Type</th><th>Status</th><th>Change status</th></tr></thead>
            <tbody><c:forEach items="${page.objects}" var="menu">
                <tr>
                    <td><c:out value="${menu.menuCode}"/></td>
                    <td><c:out value="${menu.name}"/></td>
                    <td><c:out value="${menu.menuUrl}"/></td>
                    <td><c:out value="${menu.parentMenuName}"/></td>
                    <td><c:out value="${menu.menuType}"/></td>
                    <td><c:out value="${menu.status}"/></td>
                    <td>
                        <form class="inline" action="<c:url value='/web/menus/${menu.id}/status'/>" method="post">
                            <select name="status"><c:forEach items="${statuses}" var="status"><option value="${status}" ${status == menu.status ? 'selected' : ''}>${status}</option></c:forEach></select>
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
