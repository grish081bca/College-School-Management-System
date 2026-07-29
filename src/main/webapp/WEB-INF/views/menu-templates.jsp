<%@ include file="fragments/header.jspf" %>
<div class="page-heading">
    <div><p class="eyebrow">Access administration</p>
        <h1>Menu templates</h1></div>
</div>
<section class="panel"><h2>Assign menu template</h2>
    <form class="form-grid wide" action="<c:url value='/web/menu-templates'/>" method="post">
        <label>Tenant<select name="tenantId">
            <option value="">Global</option>
            <c:forEach items="${tenants}" var="tenant"><option value="${tenant.id}">${tenant.tenantName}</option></c:forEach>
        </select></label>
        <label>User type<select name="userType" required>
            <c:forEach items="${userTypes}" var="type"><option value="${type}">${type}</option></c:forEach>
        </select></label>
        <label>Menu<select name="menuId" required>
            <c:forEach items="${menus}" var="menu"><option value="${menu.id}">${menu.menuName}</option></c:forEach>
        </select></label>
        <label>Status<select name="status">
            <c:forEach items="${statuses}" var="status"><option value="${status}">${status}</option></c:forEach>
        </select></label>
        <button class="primary" type="submit">Assign menu</button>
    </form>
</section>
<section class="panel">
    <table>
        <thead><tr><th>Scope</th><th>User type</th><th>Menu</th><th>Status</th><th>Change status</th></tr></thead>
        <tbody><c:forEach items="${templates}" var="template">
            <tr>
                <td><c:out value="${template.tenantName}"/></td>
                <td><c:out value="${template.userType}"/></td>
                <td><c:out value="${template.menuName}"/></td>
                <td><c:out value="${template.status}"/></td>
                <td>
                    <form class="inline" action="<c:url value='/web/menu-templates/${template.id}/status'/>" method="post">
                        <select name="status"><c:forEach items="${statuses}" var="status"><option value="${status}">${status}</option></c:forEach></select>
                        <button class="secondary" type="submit">Save</button>
                    </form>
                </td>
            </tr>
        </c:forEach></tbody>
    </table>
</section>
<%@ include file="fragments/footer.jspf" %>
