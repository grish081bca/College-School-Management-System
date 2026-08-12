<%@ include file="fragments/header.jspf" %>
<section class="panel">
    <div class="panel-header">
        <h2><c:out value="${user.id != null ? 'Edit user' : 'Add user'}"/></h2>
        <a class="secondary" href="<c:url value='/web/users'/>">Back to list</a>
    </div>
    <c:set var="formAction" value="/web/users"/>
    <c:if test="${user.id != null}"><c:set var="formAction" value="/web/users/${user.id}"/></c:if>
    <form action="<c:url value='${formAction}'/>" method="post" class="form-grid">
        <input type="hidden" name="id" value="${user.id}"/>
        <label>Tenant
            <select name="tenantId">
                <c:forEach items="${tenants}" var="t">
                    <option value="${t.id}" ${t.id == user.tenantId ? 'selected' : ''}>${t.tenantName}</option>
                </c:forEach>
            </select>
        </label>
        <label>Username<input name="username" value="${user.username}" required/></label>
        <label>Email<input name="email" value="${user.email}" required/></label>
        <label>First name<input name="firstName" value="${user.firstName}" required/></label>
        <label>Middle name<input name="middleName" value="${user.middleName}"/></label>
        <label>Last name<input name="lastName" value="${user.lastName}" required/></label>
        <label>Phone<input name="phoneNumber" value="${user.phoneNumber}"/></label>
        <label>Password<input name="password" type="password"/></label>
        <label>User type
            <select name="userType">
                <c:forEach items="${userTypes}" var="ut">
                    <option value="${ut}" ${ut == user.userType ? 'selected' : ''}>${ut}</option>
                </c:forEach>
            </select>
        </label>
        <label>User template
            <select name="userTemplateId">
                <option value="" ${user.userTemplateId == null ? 'selected' : ''}>None</option>
                <c:forEach items="${userTemplates}" var="ut">
                                    <option value="${ut.id}" ${user.userTemplateId != null and user.userTemplateId == ut.id ? 'selected' : ''}>${ut.name}</option>
                </c:forEach>
            </select>
        </label>
        <label>Status
            <select name="status">
                <c:forEach items="${statuses}" var="s"><option value="${s}" ${s == user.status ? 'selected' : ''}>${s}</option></c:forEach>
            </select>
        </label>
        <label>Enabled
            <input type="checkbox" name="enabled" value="true" ${user.enabled ? 'checked' : ''}/>
        </label>
        <div class="form-actions">
            <button class="primary" type="submit">Save</button>
            <a class="secondary" href="<c:url value='/web/users'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>