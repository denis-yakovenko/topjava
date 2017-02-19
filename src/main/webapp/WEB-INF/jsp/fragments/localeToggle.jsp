<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message text="${pageContext.response.locale}"/><b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li><a onclick="show('en')">English</a></li>
            <li><a onclick="show('ru')">Русский</a></li>
        </ul>
    </li>
    <script type="text/javascript">
        function show(lang) {
            window.location.href = window.location.href.split('?')[0] + '?language=' + lang;
        }
    </script>
