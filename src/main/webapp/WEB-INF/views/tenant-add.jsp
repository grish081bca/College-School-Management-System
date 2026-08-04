<%@ include file="fragments/header.jspf" %>
<%--<c:set var="pageEyebrow" value="Tenant administration"/>--%>
<%--<c:set var="pageTitle" value="Add tenant"/>--%>
<%--<c:set var="pageDescription" value="Create a tenant college with contact and location details."/>--%>
<%--<c:set var="pageActionUrl" value="/web/tenants"/>--%>
<%--<c:set var="pageActionText" value="List tenants"/>--%>
<%--<%@ include file="fragments/page-header.jspf" %>--%>
<section class="form-panel">
    <form class="form-grid enterprise-form" action="<c:url value='/web/tenants'/>" method="post">
        <label>Tenant name<input name="tenantName" required></label>
        <label>Email<input type="email" name="contactEmail" required></label>
        <label>Phone<input name="contactPhone" pattern="[0-9]{10}" required></label>
        <label>Postal code<input name="postalCode" required></label>
        <label>Country<select name="countryId" required>
            <option value="">Select country</option>
            <c:forEach items="${countries}" var="country"><option value="${country.id}">${country.name}</option></c:forEach>
        </select></label>
        <label>State<select name="stateId" required>
            <option value="">Select state</option>
            <c:forEach items="${states}" var="state"><option value="${state.id}">${state.name}</option></c:forEach>
        </select></label>
        <label>City<select name="cityId" required>
            <option value="">Select city</option>
            <c:forEach items="${cities}" var="city"><option value="${city.id}">${city.name}</option></c:forEach>
        </select></label>
        <label>Address<input name="addressLine1"></label>
        <div class="form-actions">
            <button class="primary" type="submit">Add tenant</button>
            <a class="button secondary" href="<c:url value='/web/tenants'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>
