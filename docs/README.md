# Для запуска автотестов нужно заранее установить и настроить:
**Операционная система:** Windows 10

**IDE:** intelliJ IDEA 2023.1.2 (Community Edition)

**Java:** OpenJDK 11

**Node.js** v18.18.0

**Docker Desktop** v4.24.2

# Порядок запуска автотестов с поддержкой mysql:
1. Запустить Docker Desktop.
2. Запустить intelliJ IDEA.
3. Открыть проект Graduation_project в ntelliJ IDEA
4. Запустить контейнеры командой docker-compose up –build в терминале
5. Запустить файл aqa-shop.jar командой: java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar в терминале
6. Запустить автотесты командой: ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app" в терминале
7. Получить отчет о прохождении тестов командой: ./gradlew allureserve в терминале, после прохождения всех тестов

# Порядок запуска автотестов с поддержкой postgresql:
1. Запустить Docker Desktop.
2. Запустить intelliJ IDEA.
3. Открыть проект Graduation_project в ntelliJ IDEA
4. Запустить контейнеры командой docker-compose up –build в терминале
5. Запустить файл aqa-shop.jar командой: java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar в терминале
6. Запустить автотесты командой: ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app" в терминале
7. Получить отчет о прохождении тестов командой: ./gradlew allureserve в терминале, после прохождения всех тестов

**Проектная документация находится в папке docs**

*План автоматизации Plan:*

*Отчет по итогам тестирования Report:*

*Отчет по итогам автоматизации Summary:*
