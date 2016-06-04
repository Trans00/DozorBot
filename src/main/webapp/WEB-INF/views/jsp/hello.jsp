<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>Слабоумие и Отвага Бот Админ</title>
</head>
<body>
    <h1>Слабоумие и Отвага Бот Админ</h1>
    <form name="appEnabled" method="post" action="appEnabled">
         <table>
            <tbody>
                <tr>
                    <td>Бот</td>
                    <td><input type="radio" name="appEnabled" value="true" <c:if test="${appEnabled == 'true'}">checked</c:if>>Включен</td>
                    <td><input type="radio" name="appEnabled" value="false" <c:if test="${appEnabled == 'false'}">checked</c:if>>Выключен</td>
                </tr>
            </tbody>
        </table>
        <input type="submit" value="Отправить">
    </form>
    <form name="credentials" method="post" action="credentials">
        <table>
            <tbody>
                <tr>
                    <td>Логин</td>
                    <td><input type="text" name="login" size="20" value="${credentialsRepository.login}"></td>
                </tr>
                <tr>
                    <td>Пароль</td>
                    <td><input type="text" name="password" size="20" value="${credentialsRepository.password}"></td>
                </tr>
                <tr>
                    <td>Логин игры (Только для Дозора)</td>
                    <td><input type="text" name="gamelogin" size="20" value="${credentialsRepository.gameLogin}"></td>
                </tr>
                <tr>
                    <td>Пароль игры (Только для Дозора)</td>
                    <td><input type="text" name="gamepassword" size="20" value="${credentialsRepository.gamePassword}"></td>
                </tr>
                <tr>
                    <td>Тип игры</td>
                    <td><input type="radio" name="engine" value="DZZZR" <c:if test="${plugin == 'DZZZR'}">checked</c:if>>Дозор</td>
                    <td><input type="radio" name="engine" value="ENC" <c:if test="${plugin == 'ENC'}">checked</c:if>>Энка</td>
                </tr>
                <tr>
                    <td>Url игры</td>
                    <td><input type="text" name="url" size="40" value="${credentialsRepository.url}"></td>
                </tr>
            </tbody>
        </table>
        <input type="submit" value="Отправить">
    </form>
    <form name="relogin" method="post" action="relogin">
        <input type="submit" value="Перелогиниться в движок">
    </form>
    <form name="tryEnabled" method="post" action="tryEnabled">
         <table>
            <tbody>
                <tr>
                    <td>Ввод кодов</td>
                    <td><input type="radio" name="tryEnabled" value="true" <c:if test="${tryEnabled == 'true'}">checked</c:if>>Включен</td>
                    <td><input type="radio" name="tryEnabled" value="false" <c:if test="${tryEnabled == 'false'}">checked</c:if>>Выключен</td>
                </tr>
            </tbody>
        </table>
        <input type="submit" value="Отправить">
    </form>
    <h2>Заявки</h2>
    <tbody>
        <c:forEach items="${pendingRequests}" var="user" varStatus="gameIndex">
            <tr>
            <form id="add_user" method="post" action="addUser">
                <input type="hidden" name="id" value="${user.id}">
                <td><c:out value="${user.name}"/></td>
                <button name="auth" value="true" type="submit">Свой</button>
                <button name="auth" value="false" type="submit">Чужой</button>
            </form>
            </tr>
        </c:forEach>
    </tbody>
    <h2>Игроки</h2>
    <tbody>
        <c:forEach items="${users}" var="user" varStatus="gameIndex">
            <tr>
            <form id="add_user" method="post" action="removeUser">
                <input type="hidden" name="id" value="${user.id}">
                <td><c:out value="${user.name}"/></td>
                <td><input type="submit" name="role" value="Убрать"></td>
            </form>
            </tr>
        </c:forEach>
    </tbody>
</body>
</html>
