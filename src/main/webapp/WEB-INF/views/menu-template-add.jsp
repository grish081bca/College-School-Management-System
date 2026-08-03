<%@ include file="fragments/header.jspf" %>
<c:set var="pageEyebrow" value="Access administration"/>
<c:set var="pageTitle" value="Add menu template"/>
<c:set var="pageDescription" value="Assign a menu to a user type and tenant scope."/>
<c:set var="pageActionUrl" value="/web/menu-templates"/>
<c:set var="pageActionText" value="List menu templates"/>
<%@ include file="fragments/page-header.jspf" %>
<section class="form-panel">
    <form class="form-grid enterprise-form" action="<c:url value='/web/menu-templates'/>" method="post">
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
        <div class="form-actions">
            <button class="primary" type="submit">Add menu template</button>
            <a class="button secondary" href="<c:url value='/web/menu-templates'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>
