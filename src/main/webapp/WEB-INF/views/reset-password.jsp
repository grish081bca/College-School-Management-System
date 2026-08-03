<%@ include file="fragments/header.jspf" %>
<c:set var="pageEyebrow" value="Account"/>
<c:set var="pageTitle" value="Reset password"/>
<c:set var="pageDescription" value="Reset a password with an issued reset token."/>
<%@ include file="fragments/page-header.jspf" %>
<section class="form-panel">
    <form class="form-grid enterprise-form" action="<c:url value='/web/reset-password'/>" method="post">
        <label>Reset token<input name="resetToken" required></label>
        <label>New password<input type="password" name="newPassword" required minlength="8"></label>
        <label>Confirm password<input type="password" name="confirmPassword" required minlength="8"></label>
        <div class="form-actions">
            <button class="primary" type="submit">Reset password</button>
            <a class="button secondary" href="<c:url value='/web/my-profile'/>">Cancel</a>
        </div>
    </form>
</section>
<%@ include file="fragments/footer.jspf" %>
