# JavaTranslation

JavaTranslation — это веб-приложение на Java с использованием Spring Boot, которое позволяет переводить текст с одного языка на другой. Приложение использует многопоточность для обработки запросов и сторонний API для выполнения переводов. Все запросы и их результаты сохраняются в базе данных.

## Требования

- Java 11 или выше
- Gradle 6.0 или выше
- PostgreSQL
- Доступ к Yandex.Cloud API

## Установка

1. Создайте базу данных в PostreSQL.
2. Запустите `translator/src/main/resources/init.sql` в консоли БД.
3. Откройте файл `translator/src/main/java/com/example/translation/dao/TranslationDAO.java` и замените `URL`, `USER`, `PASSWORD` на свои значения.
4. Запустите Maven: 
`mvn spring-boot:run`.
5. Откройте браузер и введите `/localhost:8080/`.
