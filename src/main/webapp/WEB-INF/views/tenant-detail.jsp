<%@ include file="fragments/header.jspf" %>
<section class="panel">
    <div class="panel-header">
        <h2>Tenant details</h2>
        <a class="secondary" href="<c:url value='/web/tenants'/>">Back to list</a>
    </div>
    <div class="detail-grid">
        <div><strong>Code</strong></div><div><c:out value="${tenant.tenantCode}"/></div>
        <div><strong>Name</strong></div><div><c:out value="${tenant.tenantName}"/></div>
        <div><strong>Email</strong></div><div><c:out value="${tenant.contactEmail}"/></div>
        <div><strong>Secondary email</strong></div><div><c:out value="${tenant.contactEmailSecondary}"/></div>
        <div><strong>Phone</strong></div><div><c:out value="${tenant.contactPhone}"/></div>
        <div><strong>Secondary phone</strong></div><div><c:out value="${tenant.contactPhoneSecondary}"/></div>
        <div><strong>Address line 1</strong></div><div><c:out value="${tenant.addressLine1}"/></div>
        <div><strong>Address line 2</strong></div><div><c:out value="${tenant.addressLine2}"/></div>
        <div><strong>Country</strong></div><div><c:out value="${tenant.countryName}"/></div>
        <div><strong>State</strong></div><div><c:out value="${tenant.stateName}"/></div>
        <div><strong>City</strong></div><div><c:out value="${tenant.cityName}"/></div>
        <div><strong>Postal code</strong></div><div><c:out value="${tenant.postalCode}"/></div>
        <div><strong>Status</strong></div><div><c:out value="${tenant.status}"/></div>
    </div>
</section>
<%@ include file="fragments/footer.jspf" %>
