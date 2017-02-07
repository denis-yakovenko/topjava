<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>




<div class="jumbotron">
    <div class="container">
        <div class="shadow">
    <h3><spring:message code="meals.title"/></h3>

            <form class="form-horizontal" method="post" id="filterForm">
                <dl>
                    <dt><spring:message code="meals.startDate"/>:</dt>
                    <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code="meals.endDate"/>:</dt>
                    <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code="meals.startTime"/>:</dt>
                    <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code="meals.endTime"/>:</dt>
                    <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
                </dl>
                <button class="btn btn-success" type="button" onclick="updateTable()"><spring:message code="meals.filter"/></button>
                <button class="btn btn-danger" type="button" onclick="$('#filterForm').trigger('reset');updateTable();"><spring:message code="meals.reset"/></button>
            </form>

            <div class="view-box">
                <a class="btn btn-sm btn-info" onclick="add()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                </a>

    <table class="table table-striped display" id="datatable">
        <thead>
        <tr>
            <th><spring:message code="meals.dateTime"/></th>
            <th><spring:message code="meals.description"/></th>
            <th><spring:message code="meals.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr id="${meal.id}" class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>
                    <a class="btn btn-xs btn-primary edit">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    </a>
                </td>
                <td>
                    <a class="btn btn-xs btn-danger delete">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><spring:message code="meals.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3"><spring:message code="meals.dateTime"/></label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime" placeholder="<spring:message code="meals.dateTime"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message code="meals.description"/></label>

                        <div class="col-xs-9">
                            <input type="text" size="40" class="form-control" id="description" name="description" placeholder="<spring:message code="meals.description"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3"><spring:message code="meals.calories"/></label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories" placeholder="<spring:message code="meals.calories"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
