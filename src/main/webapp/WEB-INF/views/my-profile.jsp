<%@ include file="fragments/header.jspf" %>
<c:set var="pageEyebrow" value="Account"/>
<c:set var="pageTitle" value="My profile"/>
<c:set var="pageDescription" value="Review your current account, tenant and session information."/>
<%@ include file="fragments/page-header.jspf" %>
<section class="panel">
    <div class="table-wrap">
        <table class="data-table">
            <tbody>
            <tr><th>Name</th><td><c:out value="${currentUserFullName}"/></td></tr>
            <tr><th>Username</th><td><c:out value="${currentUsername}"/></td></tr>
            <tr><th>User type</th><td><c:out value="${loggedInUserType}"/></td></tr>
            <tr><th>Tenant</th><td><c:out value="${currentTenantCode}"/></td></tr>
            <tr><th>Last login</th><td><c:out value="${empty currentLastLoginAt ? 'Not available' : currentLastLoginAt}"/></td></tr>
            <tr><th>Last password change</th><td><c:out value="${empty currentPasswordChangedAt ? 'Not available' : currentPasswordChangedAt}"/></td></tr>
            </tbody>
        </table>
    </div>
</section>
<%@ include file="fragments/footer.jspf" %>
