<%@ include file="fragments/header.jspf" %>
<div class="page-heading">
    <div><p class="eyebrow">Tenant administration</p>
        <h1>Tenants</h1></div>
</div>
<section class="panel"><h2>Create tenant</h2>
    <form class="form-grid wide" action="<c:url value='/web/tenants'/>" method="post"><label>Tenant name<input
            name="tenantName" required></label><label>Email<input type="email" name="contactEmail"
                                                                  required></label><label>Phone<input
            name="contactPhone" pattern="[0-9]{10}" required></label><label>Postal code<input name="postalCode"
                                                                                              required></label><label>Country<select
            name="countryId" required>
        <option value="">Select country</option>
        <c:forEach items="${countries}" var="country">
            <option value="${country.id}">${country.name}</option>
        </c:forEach></select></label><label>State<select name="stateId" required>
        <option value="">Select state</option>
        <c:forEach items="${states}" var="state">
            <option value="${state.id}">${state.name}</option>
        </c:forEach></select></label><label>City<select name="cityId" required>
        <option value="">Select city</option>
        <c:forEach items="${cities}" var="city">
            <option value="${city.id}">${city.name}</option>
        </c:forEach></select></label><label>Address<input name="addressLine1"></label>
        <button class="primary" type="submit">Create tenant</button>
    </form>
</section>
<section class="panel">
    <table>
        <thead>
        <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Contact</th>
            <th>Location</th>
            <th>Status</th>
            <th>Change status</th>
        </tr>
        </thead>
        <tbody><c:forEach items="${tenants}" var="tenant">
            <tr>
                <td><c:out value="${tenant.tenantCode}"/></td>
                <td><c:out value="${tenant.tenantName}"/></td>
                <td><c:out value="${tenant.contactEmail}"/><br><c:out value="${tenant.contactPhone}"/></td>
                <td><c:out value="${tenant.cityName}"/>, <c:out value="${tenant.stateName}"/></td>
                <td><c:out value="${tenant.status}"/></td>
                <td>
                    <form class="inline" action="<c:url value='/web/tenants/${tenant.id}/status'/>" method="post">
                        <select name="status"><c:forEach items="${statuses}" var="status">
                            <option value="${status}">${status}</option>
                        </c:forEach></select>
                        <button class="secondary" type="submit">Save</button>
                    </form>
                </td>
            </tr>
        </c:forEach></tbody>
    </table>
</section>
<%@ include file="fragments/footer.jspf" %>
