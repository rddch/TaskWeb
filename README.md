# votingApp

## Requirements

install Java 1.8 or higher

install mysql 5.7.17 or higher

create "app" database 

MySql login and password: root

## Build & development

To start a project use:
`git clone https://github.com/erizoo/app`

`cd app`

`mvn spring-boot:run`

project will be available on http://localhost:8081/ 

## Specification

"Приложение-голосовалка"

Написать серверное standalone приложение со следующим функционалом : 
1) создает темы для голосования,
2) Старт голосования с генерацией ссылки для голосования 
3) Закрытие голосования 
4) Отображение статистики (в виде Выбранный пункт - количество)
5) Получение данных о голосовании (по сгенерированной ссылке)
6) Регистрация голоса

С сервером общение посредством REST API, данные в формате Json.

Обязательные технологии: Java8, Spring boot
