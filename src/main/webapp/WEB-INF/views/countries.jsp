<%@ include file="fragments/header.jspf" %>
<div class="page-heading">
    <div><p class="eyebrow">Location setup</p>
        <h1>Countries</h1></div>
</div>
<section class="panel"><h2>Add country</h2>
    <form class="form-grid" action="<c:url value='/web/countries'/>" method="post"><label>Name<input name="name"
                                                                                                     required
                                                                                                     maxlength="100"></label><label>ISO
        code<input name="isoCode" required maxlength="3"></label>
        <button class="primary" type="submit">Add country</button>
    </form>
</section>
<section class="panel">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>ISO code</th>
            <th></th>
        </tr>
        </thead>
        <tbody><c:forEach items="${countries}" var="country">
            <tr>
                <td>${country.id}</td>
                <td><c:out value="${country.name}"/></td>
                <td><c:out value="${country.isoCode}"/></td>
                <td>
                    <form action="<c:url value='/web/countries/${country.id}/delete'/>" method="post">
                        <button class="danger" type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach></tbody>
    </table>
</section>
<%@ include file="fragments/footer.jspf" %>
