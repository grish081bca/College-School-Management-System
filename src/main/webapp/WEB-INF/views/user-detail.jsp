<%@ include file="fragments/header.jspf" %>
<section class="panel">
    <div class="panel-header">
        <h2>User details</h2>
        <a class="secondary" href="<c:url value='/web/users'/>">Back to list</a>
    </div>
    <div class="detail-grid">
        <div><strong>Username</strong></div><div><c:out value="${user.username}"/></div>
        <div><strong>Name</strong></div><div><c:out value="${user.firstName}"/> <c:out value="${user.middleName}"/> <c:out value="${user.lastName}"/></div>
        <div><strong>Email</strong></div><div><c:out value="${user.email}"/></div>
        <div><strong>Phone</strong></div><div><c:out value="${user.phoneNumber}"/></div>
        <div><strong>Tenant</strong></div><div><c:out value="${user.tenantName}"/></div>
        <div><strong>User template</strong></div><div><c:out value="${user.userTemplateName != null ? user.userTemplateName : ''}"/></div>
        <div><strong>User type</strong></div><div><c:out value="${user.userType}"/></div>
        <div><strong>Status</strong></div><div><c:out value="${user.status}"/></div>
        <div><strong>Enabled</strong></div><div><c:out value="${user.enabled}"/></div>
    </div>
</section>
<%@ include file="fragments/footer.jspf" %>