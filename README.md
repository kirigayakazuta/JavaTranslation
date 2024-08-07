# JavaTranslation

## Установка
1. Создайте базу данных в PostreSQL.
2. Запустите `translator/src/main/resources/init.sql` в консоли БД.
3. Откройте файл `translator/src/main/java/com/example/translation/dao/TranslationDAO.java` и замените `URL`, `USER`, `PASSWORD` на свои значения.
4. Запустите Maven: 
`mvn spring-boot:run`.
5. Откройте браузер и введите `/localhost:8080/`.