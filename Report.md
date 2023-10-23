# Отчет по итогам тестирования
## **130** тест-кейсов:
#### **94** - успешные
#### **36** - не успешные

### В процентном соотношении:
**72,3% - успешные, 27,7% - не успешные**

## Краткое описание

1. **PaymentPageTest** - тесты на заполнение валидными и невалидными данными формы "Оплата по карте"
- 59 тестов, из них:
- 43 - успешные
- 16 - не успешные

2. **CreditPageTest** - тесты на заполнение валидными и невалидными данными формы "Кредит по данным карты"
- 59 тестов, из них:
- 43 - успешные
- 16 - не успешные

3. **MySQLPaymenTest** - тесты (запросы) в БД MySQL, проверяющие корректность внесения информации приложением из формы "Оплата по карте"
- 4 теста, из них:
- 2 - успешные
- 2 - не успешные

4. **MySQLCreditTest** - тесты (запросы) в БД MySQL, проверяющие корректность внесения информации приложением из формы "Кредит по данным карты"
- 2 теста, из них:
- 2 - успешные
- 0 - не успешные

5. **PostgresPaymenTest** - тесты (запросы) в БД Postgres, проверяющие корректность внесения информации приложением из формы "Оплата по карте"
- 4 теста, из них:
- 2 - успешные
- 2 - не успешные

6. **PostgresCreditTest** - тесты (запросы) в БД Postgres, проверяющие корректность внесения информации приложением из формы "Кредит по данным карты"
- 2 теста, из них:
- 2 - успешные
- 0 - не успешные

**Oбщие рекомендации:**

В систему автоматизации интегрированы отчёты: Gradle, Allure