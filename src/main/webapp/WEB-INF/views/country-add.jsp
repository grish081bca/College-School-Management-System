<%@ include file="fragments/header.jspf" %>
<c:set var="pageEyebrow" value="Location setup"/>
<c:set var="pageTitle" value="Add country"/>
<c:set var="pageDescription" value="Create a country record for tenant and location setup."/>
<c:set var="pageActionUrl" value="/web/countries"/>
<c:set var="pageActionText" value="List countries"/>
<%@ include file="fragments/page-header.jspf" %>
<section class="form-panel">
    <form class="form-grid enterprise-form" action="<c:url value='/web/countries'/>" method="post">
        <label>Name<input name="name" required maxlength="100"></label>
        <label>ISO code<input name="isoCode" required maxlength="3"></label>
        <div class="form-actions">
            <button class="primary" type="submit">Add country</button>
            <a class="button secondary" href="<c:url value='/web/countries'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>
