package ru.netology.diploma.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.data.SQLHelper;
import ru.netology.diploma.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.diploma.data.SQLHelper.cleanDatabasePostgres;

public class PostgresPaymentTest {
    PaymentPage paymentPage;
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    void setUp() {
        paymentPage = open("http://localhost:8080", PaymentPage.class);
        paymentPage.goToThePaymentPage();
    }
    @AfterEach
    void tearDown() {
        cleanDatabasePostgres();
    }

    @Test
    void sendingPaymentFormWithApprovedValidDataGetStatusPostgres() { //отправка формы c Одобренными Действительными Данными получить статус
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
        assertEquals("APPROVED",
                SQLHelper.getPaymentStatusInfoPostgres().getStatus());
    }

    @Test
    void sendingPaymentFormWithApprovedValidDataGetTheAmountPostgres() { //отправка формы c Одобренными Данными получить сумму
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
        assertEquals(45000,
                SQLHelper.getAmountInfoPostgres().getAmount());//
    }

    @Test
    void sendingPaymentFormWithDeclinedValidDataGetStatusPostgres() { //отправка формы c Оотклоненными Действительными Данными получить статус
        paymentPage.fillingInTheCardNumberField(DataHelper.getSecondCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
        assertEquals("DECLINED",
                SQLHelper.getPaymentStatusInfoPostgres().getStatus());
    }

    @Test
    void sendingPaymentFormWithDeclinedValidDataGetTheAmountPostgres() { //отправка формы c Оотклоненными Данными получить сумму
        paymentPage.fillingInTheCardNumberField(DataHelper.getSecondCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
        assertEquals(0,
                SQLHelper.getAmountInfoPostgres().getAmount());//
    }
}
