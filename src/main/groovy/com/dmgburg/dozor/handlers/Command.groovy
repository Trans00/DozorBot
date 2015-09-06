package com.dmgburg.dozor.handlers

enum Command {
    CANCEL(command : "cancel",description: "Отмена ввода данных"),
    HELP(command : "help",description: "Подсказка и информация о доступных действиях"),
    KS(command : "ks",description: "Коды сложности на уровне"),
    KSNEW(command : "ksnew",description: "Ручной ввод кодов сложности на уровне"),
    PASSED(command : "passed",description: "Отметить, что код взят"),
    ADMIN(command : "admin",description: "Режим администратора"),
    LOGIN(command : "login",description: "Изменение логина"),
    PASSWORD(command : "password",description: "Изменение пароля"),
    GAME_LOGIN(command : "gamelogin",description: "Изменение логина игры"),
    GAME_PASSWORD(command : "gamepassword",description: "Изменение пароля игры"),
    URL(command : "url",description: "Изменение url"),
    MANAGE_USERS(command : "manageusers",description: "Заявки от пользователей"),
    START(command : "start",description: "Зарегистрироваться в системе"),
    TEA(command : "tea",description: "Попросить чаю"),
    WANT(command : "want",description: "Оставить хотелку")

    String command
    String description
}
