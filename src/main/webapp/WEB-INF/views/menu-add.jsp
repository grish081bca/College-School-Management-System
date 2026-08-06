<%@ include file="fragments/header.jspf" %>
<%--<c:set var="pageEyebrow" value="Access administration"/>--%>
<%--<c:set var="pageTitle" value="Add menu"/>--%>
<%--<c:set var="pageDescription" value="Create a super menu or sub-menu for the dynamic sidebar."/>--%>
<%--<c:set var="pageActionUrl" value="/web/menus"/>--%>
<%--<c:set var="pageActionText" value="List menus"/>--%>
<%--<%@ include file="fragments/page-header.jspf" %>--%>
<section class="form-panel">
    <form class="form-grid enterprise-form" action="<c:url value='/web/menus'/>" method="post">
        <label>Code<input name="menuCode" required></label>
        <label>Name<input name="name" required></label>
        <label>URL<input name="menuUrl"></label>
        <label>Icon<input name="icon"></label>
        <label>Super menu<select name="parentMenuId">
            <option value="">No parent</option>
            <c:forEach items="${activeMenus}" var="parent">
                <c:if test="${empty parent.parentMenuId}">
                                    <option value="${parent.id}">${parent.name}</option>
                </c:if>
            </c:forEach>
        </select></label>
        <label>Display order<input type="number" name="displayOrder" value="0"></label>
        <label>Type<select name="menuType">
            <c:forEach items="${menuTypes}" var="type"><option value="${type}">${type}</option></c:forEach>
        </select></label>
        <label>Status<select name="status">
            <c:forEach items="${statuses}" var="status"><option value="${status}">${status}</option></c:forEach>
        </select></label>
        <div class="form-actions">
            <button class="primary" type="submit">Add menu</button>
            <a class="button secondary" href="<c:url value='/web/menus'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>
