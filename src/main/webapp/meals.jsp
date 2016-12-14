<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        th{
            font-weight: bold;
            background-color: lightgray;
        }
        tr.exceedTrue{
            background-color: orangered;
        }
        tr.exceedFalse{
            background-color: lightgreen;
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table border="1">
    <tr>
        <th>id</th>
        <th>dateTime</th>
        <th>description</th>
        <th>calories</th>
        <th>edit</th>
        <th>delete</th>
    </tr>
    <c:forEach items="${meals}" var="mealItem">
        <tr
                <c:choose>
                    <c:when test="${mealItem.exceed}">
                        class="exceedTrue"
                    </c:when>
                    <c:otherwise>
                        class="exceedFalse"
                    </c:otherwise>
                </c:choose>
        >
            <td>${mealItem.id}</td>
            <td>${mealItem.dateTime.toLocalDate()} ${mealItem.dateTime.toLocalTime()}</td>
            <td>${mealItem.description}</td>
            <td>${mealItem.calories}</td>
            <td><a href="<c:url value='meals?action=edit&id=${mealItem.id}' />">edit</a></td>
            <td><a href="<c:url value='meals?action=delete&id=${mealItem.id}' />">delete</a></td>
        </tr>
    </c:forEach>
</table>
<h2>
    <c:choose>
        <c:when test="${edit}">
            Edit meal
        </c:when>
        <c:otherwise>
            Add new meal
        </c:otherwise>
    </c:choose>
</h2>
<form method="POST" >
    <input type="hidden" name="id" value="${id}"/>
    <input type="hidden" name="action" value="update"/>
    <label for="dateTime">dateTime(yyyy-MM-ddTHH:mm)</label>
    <br>
    <input required id="dateTime" name="dateTime" value="${meal.dateTime}" type="datetime-local"/>
    <br>
    <label for="description">description</label>
    <br>
    <input required id="description" name="description" value="${meal.description}"/>
    <br>
    <label for="calories">calories</label>
    <br>
    <input required id="calories" name="calories" value="${meal.calories}" type="number"/>
    <br>
    <c:choose>
        <c:when test="${edit}">
            <input type="submit" value="Save"/>
        </c:when>
        <c:otherwise>
            <input type="submit" value="Add"/>
        </c:otherwise>
    </c:choose>
</form>
</body>
</html>
