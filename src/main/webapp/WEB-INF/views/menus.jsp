<%@ include file="fragments/header.jspf" %>
<div class="page-heading">
    <div><p class="eyebrow">Access administration</p>
        <h1>Menus</h1></div>
</div>
<section class="panel"><h2>Create menu</h2>
    <form class="form-grid wide" action="<c:url value='/web/menus'/>" method="post">
        <label>Code<input name="menuCode" required></label>
        <label>Name<input name="menuName" required></label>
        <label>URL<input name="menuUrl"></label>
        <label>Icon<input name="icon"></label>
        <label>Parent<select name="parentMenuId">
            <option value="">No parent</option>
            <c:forEach items="${activeMenus}" var="parent">
                <option value="${parent.id}">${parent.menuName}</option>
            </c:forEach>
        </select></label>
        <label>Display order<input type="number" name="displayOrder" value="0"></label>
        <label>Type<select name="menuType">
            <c:forEach items="${menuTypes}" var="type">
                <option value="${type}">${type}</option>
            </c:forEach>
        </select></label>
        <label>Status<select name="status">
            <c:forEach items="${statuses}" var="status">
                <option value="${status}">${status}</option>
            </c:forEach>
        </select></label>
        <button class="primary" type="submit">Create menu</button>
    </form>
</section>
<section class="panel">
    <table>
        <thead><tr><th>Code</th><th>Name</th><th>URL</th><th>Parent</th><th>Type</th><th>Status</th><th>Change status</th></tr></thead>
        <tbody><c:forEach items="${menus}" var="menu">
            <tr>
                <td><c:out value="${menu.menuCode}"/></td>
                <td><c:out value="${menu.menuName}"/></td>
                <td><c:out value="${menu.menuUrl}"/></td>
                <td><c:out value="${menu.parentMenuName}"/></td>
                <td><c:out value="${menu.menuType}"/></td>
                <td><c:out value="${menu.status}"/></td>
                <td>
                    <form class="inline" action="<c:url value='/web/menus/${menu.id}/status'/>" method="post">
                        <select name="status"><c:forEach items="${statuses}" var="status"><option value="${status}">${status}</option></c:forEach></select>
                        <button class="secondary" type="submit">Save</button>
                    </form>
                </td>
            </tr>
        </c:forEach></tbody>
    </table>
</section>
<%@ include file="fragments/footer.jspf" %>
