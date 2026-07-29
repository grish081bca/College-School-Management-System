<%@ include file="fragments/header.jspf" %>
<div class="page-heading">
    <div><p class="eyebrow">Location setup</p>
        <h1>Cities</h1></div>
</div>
<section class="panel"><h2>Add city</h2>
    <form class="form-grid" action="<c:url value='/web/cities'/>" method="post"><label>Name<input name="name" required
                                                                                                  maxlength="100"></label><label>State<select
            name="stateId" required>
        <option value="">Select state</option>
        <c:forEach items="${states}" var="state">
            <option value="${state.id}"><c:out value="${state.name}"/> — <c:out value="${state.countryName}"/></option>
        </c:forEach></select></label>
        <button class="primary" type="submit">Add city</button>
    </form>
</section>
<section class="panel">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>State</th>
            <th>Country</th>
            <th></th>
        </tr>
        </thead>
        <tbody><c:forEach items="${cities}" var="city">
            <tr>
                <td>${city.id}</td>
                <td><c:out value="${city.name}"/></td>
                <td><c:out value="${city.stateName}"/></td>
                <td><c:out value="${city.countryName}"/></td>
                <td>
                    <form action="<c:url value='/web/cities/${city.id}/delete'/>" method="post">
                        <button class="danger" type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach></tbody>
    </table>
</section>
<%@ include file="fragments/footer.jspf" %>
