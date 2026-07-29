<%@ include file="fragments/header.jspf" %>
<div class="page-heading">
    <div><p class="eyebrow">Location setup</p>
        <h1>States</h1></div>
</div>
<section class="panel"><h2>Add state</h2>
    <form class="form-grid" action="<c:url value='/web/states'/>" method="post"><label>Name<input name="name" required
                                                                                                  maxlength="100"></label><label>Country<select
            name="countryId" required>
        <option value="">Select country</option>
        <c:forEach items="${countries}" var="country">
            <option value="${country.id}"><c:out value="${country.name}"/></option>
        </c:forEach></select></label>
        <button class="primary" type="submit">Add state</button>
    </form>
</section>
<section class="panel">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Country</th>
            <th></th>
        </tr>
        </thead>
        <tbody><c:forEach items="${states}" var="state">
            <tr>
                <td>${state.id}</td>
                <td><c:out value="${state.name}"/></td>
                <td><c:out value="${state.countryName}"/></td>
                <td>
                    <form action="<c:url value='/web/states/${state.id}/delete'/>" method="post">
                        <button class="danger" type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach></tbody>
    </table>
</section>
<%@ include file="fragments/footer.jspf" %>
