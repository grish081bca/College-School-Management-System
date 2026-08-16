<%@ include file="fragments/header.jspf" %>
<%--<c:set var="pageEyebrow" value="Tenant administration"/>--%>
<%--<c:set var="pageTitle" value="Add tenant"/>--%>
<%--<c:set var="pageDescription" value="Create a tenant college with contact and location details."/>--%>
<%--<c:set var="pageActionUrl" value="/web/tenants"/>--%>
<%--<c:set var="pageActionText" value="List tenants"/>--%>
<%--<%@ include file="fragments/page-header.jspf" %>--%>
<section class="form-panel">
    <c:choose>
        <c:when test="${isEdit}">
            <c:url var="tenantFormAction" value="/web/tenants/${tenantId}"/>
        </c:when>
        <c:otherwise>
            <c:url var="tenantFormAction" value="/web/tenants"/>
        </c:otherwise>
    </c:choose>
    <form class="form-grid enterprise-form" action="${tenantFormAction}" method="post">
        <label>Tenant name<input name="tenantName" value="<c:out value='${tenantRequest.tenantName}'/>" required></label>
        <label>Email<input type="email" name="contactEmail" value="<c:out value='${tenantRequest.contactEmail}'/>" required></label>
        <label>Secondary email (optional)<input type="email" name="contactEmailSecondary" value="<c:out value='${tenantRequest.contactEmailSecondary}'/>"></label>
        <label>Phone<input name="contactPhone" value="<c:out value='${tenantRequest.contactPhone}'/>" pattern="[0-9]{10}" required></label>
        <label>Secondary phone (optional)<input name="contactPhoneSecondary" value="<c:out value='${tenantRequest.contactPhoneSecondary}'/>" maxlength="15"></label>
        <label>Postal code<input name="postalCode" value="<c:out value='${tenantRequest.postalCode}'/>" required></label>
        <label>Country<select name="countryId" required>
            <option value="">Select country</option>
            <c:forEach items="${countries}" var="country"><option value="${country.id}" ${country.id == tenantRequest.countryId ? 'selected' : ''}>${country.name}</option></c:forEach>
        </select></label>
        <label>State<select name="stateId" required>
            <option value="">Select state</option>
            <c:forEach items="${states}" var="state"><option value="${state.id}" ${state.id == tenantRequest.stateId ? 'selected' : ''}>${state.name}</option></c:forEach>
        </select></label>
        <label>City<select name="cityId" required>
            <option value="">Select city</option>
            <c:forEach items="${cities}" var="city"><option value="${city.id}" ${city.id == tenantRequest.cityId ? 'selected' : ''}>${city.name}</option></c:forEach>
        </select></label>
        <label>Address line 1 (optional)<input name="addressLine1" value="<c:out value='${tenantRequest.addressLine1}'/>"></label>
        <label>Address line 2 (optional)<input name="addressLine2" value="<c:out value='${tenantRequest.addressLine2}'/>"></label>
        <c:if test="${isEdit}">
            <label>Remarks (optional)<textarea name="remarks" rows="3"></textarea></label>
        </c:if>
        <div class="form-actions">
            <button class="primary" type="submit">${isEdit ? 'Update tenant' : 'Add tenant'}</button>
            <a class="button secondary" href="<c:url value='/web/tenants'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>
