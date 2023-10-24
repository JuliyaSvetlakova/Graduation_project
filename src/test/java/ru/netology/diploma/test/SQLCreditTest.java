package ru.netology.diploma.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.data.SQLHelper;
import ru.netology.diploma.page.CreditPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.diploma.data.SQLHelper.cleanDatabaseSQL;

public class SQLCreditTest {
    CreditPage creditPage;
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
        creditPage = open("http://localhost:8080", CreditPage.class);
        creditPage.goToTheCreditPage();
    }

    @AfterEach
    void tearDown() {
        cleanDatabaseSQL();
    }

    @Test
    void sendingCreditFormWithApprovedValidDataGetStatusMySQL() { //отправка формы c Одобренными Действительными Данными получить статус
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
        assertEquals("APPROVED",
                SQLHelper.getCreditStatusInfoSQL().getStatus());
    }

    @Test
    void sendingCreditFormWithDeclinedValidDataGetStatusMySQL() { //отправка формы c Оотклоненными Действительными Данными получить статус
        creditPage.fillingInTheCardNumberField(DataHelper.getSecondCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
        assertEquals("DECLINED",
                SQLHelper.getCreditStatusInfoSQL().getStatus());
    }
}
