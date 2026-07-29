<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login | College ERP</title>
    <link rel="stylesheet" href="<c:url
        value='/css/app.css'/>">
</head>
<body class="auth-page">
<div class="auth-card"><h1>College ERP</h1>
    <p class="muted">Sign in to manage your college tenants.</p><c:if
            test="${not empty param.error}">
        <div class="alert error">Invalid username or password.</div>
    </c:if><c:if test="${not empty param.logout}">
        <div class="alert success">You have been signed out.</div>
    </c:if>
    <form action="<c:url value='/login'/>" method="post"><label>Username<input
            name="username" required></label><label>Password<input type="password" name="password"
                                                                   required></label>
        <button class="primary" type="submit">Sign in</button>
    </form>
</div>
</body>
</html>
