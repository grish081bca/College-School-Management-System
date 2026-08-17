<%@ include file="fragments/header.jspf" %>
<section class="form-panel">
    <c:choose>
        <c:when test="${isEdit}">
            <c:url var="tenantFormAction" value="/web/tenant-branches/${tenantId}"/>
        </c:when>
        <c:otherwise>
            <c:url var="tenantFormAction" value="/web/tenant-branches"/>
        </c:otherwise>
    </c:choose>
    <form class="form-grid enterprise-form" action="${tenantFormAction}" method="post">
        <c:choose>
            <c:when test="${isEdit}">
                <label>Main tenant
                    <input value="<c:out value='${tenant.parentTenantName}'/> - <c:out value='${tenant.parentTenantCode}'/>" readonly>
                </label>
                <label>Branch code
                    <input value="<c:out value='${tenant.tenantCode}'/>" readonly>
                </label>
            </c:when>
            <c:otherwise>
                <label>Main tenant
                    <select name="parentTenantId" required>
                        <option value="">Select main tenant</option>
                        <c:forEach items="${mainTenants}" var="mainTenant">
                            <option value="${mainTenant.id}" ${mainTenant.id == tenantRequest.parentTenantId ? 'selected' : ''}>
                                <c:out value="${mainTenant.tenantName}"/> - <c:out value="${mainTenant.tenantCode}"/>
                            </option>
                        </c:forEach>
                    </select>
                </label>
            </c:otherwise>
        </c:choose>
        <label>Branch name<input name="tenantName" value="<c:out value='${tenantRequest.tenantName}'/>" required></label>
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
            <button class="primary" type="submit">${isEdit ? 'Update branch' : 'Add branch'}</button>
            <a class="button secondary" href="<c:url value='/web/tenant-branches'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>
