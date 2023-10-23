package ru.netology.diploma.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.page.PaymentPage;

import static com.codeborne.selenide.Selenide.*;


public class PaymentPageTest {
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

    @Test
    void sendingPaymentFormWithAllValidData(){ //отправка формы со Всеми Действительными Данными
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithCardNumberInTheRejectedStatus(){ //отправка формы c номером карты в статусе отклонено
        paymentPage.fillingInTheCardNumberField(DataHelper.getSecondCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyErrorNotification("Ошибка! Банк отказал в проведении операции.");//
    }
    @Test
    void sendingPaymentBlankForm(){ //отправка незаполненной формы
        paymentPage.submittingForm();

        paymentPage.verifyNotificationAboutCardNumberEntryError("Неверный формат");
        paymentPage.verifyNotificationAboutMonthEntryError("Неверный формат");
        paymentPage.verifyNotificationAboutYearEntryError("Неверный формат");
        paymentPage.verifyNotificationAboutHolderEntryError("Поле обязательно для заполнения");
        paymentPage.verifyNotificationAboutCardCodeEntryError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithBlankCardNumberData(){ //отправка формы с незаполненными Данными номера карты
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithBlankMonthData(){ //отправка формы с незаполненными Данными Месяца
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithBlankYearData(){ //отправка формы с незаполненными Данными Года
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithBlankHolderData(){ //отправка формы с незаполненными Данными Владельца
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Поле обязательно для заполнения");
    }
    @Test
    void sendingPaymentFormWithBlankCardCodeData(){ //отправка формы с незаполненными Данными кода карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    //Заполнение поля номер карты невалидными данными
    @Test
    void sendingPaymentFormWithFifteenDigitsInCardNumberField(){ //отправка формы c 15 цифрами в поле номер карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithFifteenDigits());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithSeventeenDigitsInCardNumberField(){ //отправка формы c 17 цифрами в поле номер карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithSeventeenDigits());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyErrorNotification("Ошибка! Банк отказал в проведении операции.");
    }
    @Test
    void sendingPaymentFormWithAllZerosInCardNumberField(){ //отправка формы cо всеми нулями в поле номер карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithAllZeros());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithLatinCharactersInCardNumberField(){ //отправка формы c латинскими сиволами в поле номер карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithLatinCharacters());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithCyrillicCharactersInCardNumberField(){ //отправка формы c символами на кириллице в поле номер карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithCyrillicCharacters());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithSpecialCharactersInCardNumberField(){ //отправка формы со спецсимволами в поле номер карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithSpecialCharacters());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithIeroglyphicsInCardNumberField(){ //отправка формы с иероглифами в поле номер карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithIeroglyphics());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithArabicScriptInCardNumberField(){ //отправка формы с арабской вязью в поле номер карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithArabicScript());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }

    //Срок действия карты
    @Test
    void sendingPaymentFormWithAnExpiredCardForMonth(){ //отправка формы с истеченным сроком действия карты на месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getLastMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getCurrentYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }
    @Test
    void sendingPaymentFormWithTheExpirationOfTheCardInTheCurrentMonth(){ //отправка формы с истеченнмем срока действия карты в текущем месяце
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getCurrentYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithTheExpirationOfTheCardInTheNextMonth(){ //отправка формы с истеченнмем срока действия карты в следующем месяце
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getNextMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getCurrentYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithAnExpiredCardForYear(){ //отправка формы с истеченным сроком действия карты на год
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getLastYear());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Истёк срок действия карты");
    }
    @Test
    void sendingPaymentFormWithTheExpirationDateOfTheCardNextYear(){ //отправка формы с истечением срока действия карты в следующем году
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithDataIncludedInTheFiveYearValidityPeriodOfTheCardLessThanMonth(){ //отправка формы с данными входящими в пятилетний срок действия карты, меньше на месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getLastMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearBeforeTheCardExpires());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithDataIncludedInTheFiveYearValidityPeriodOfTheCard(){ //отправка формы с данными входящими в пятилетний срок действия карты
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearBeforeTheCardExpires());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithDataExceedingTheFiveYearValidityPeriodOfTheCardForMonth(){ //отправка формы с данными превышающими пятилетний срок действия карты на месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getNextMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearBeforeTheCardExpires());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");//
    }
    @Test
    void sendingPaymentFormWithDataExceedingTheFiveYearValidityPeriodOfTheCardForYear(){ //отправка формы с данными превышающими пятилетний срок действия карты на год
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getExpirationYearOfTheCard());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }

    //Заполнение поля месяц невалидными данными
    @Test
    void sendingPaymentFormWithSingleDigitInTheMonthField(){ //отправка формы с одной цифрой в поле месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthWithOneDigit());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithThreeDigitsInTheMonthField(){ //отправка формы с тремя цифрами в поле месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthWithThreeDigits());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }
    @Test
    void sendingPaymentFormWithAllZerosInTheMonthField(){ //отправка формы со всеми нолями в поле месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthWithAllZeros());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");//
    }
    @Test
    void sendingPaymentFormWithTheThirteenthMonthInTheMonthField(){ //отправка формы с тринадцатым месяцем в поле месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getThirteenthMonth());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }
    @Test
    void sendingPaymentFormWithLatinCharactersInMonthField(){ //отправка формы c латинскими сиволами в поле месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthWithLatinCharacters());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithCyrillicCharactersInMonthField(){ //отправка формы c сиволами на кириллице в поле месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthWithCyrillicCharacters());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithSpecialCharactersInMonthField(){ //отправка формы со спецсиволами в поле месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthWithSpecialCharacters());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithIeroglyphicsInMonthField(){ //отправка формы с иероглифами в поле месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthWithIeroglyphics());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithArabicScriptInMonthField(){ //отправка формы с арабской вязью в поле месяц
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthWithArabicScript());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    //Заполнение поля год невалидными данными
    @Test
    void sendingPaymentFormWithSingleDigitInTheYearField(){ //отправка формы с одной цифрой в поле год
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearWithOneDigit());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithThreeDigitsInTheYearField(){ //отправка формы с тремя цифрами в поле год
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearWithThreeDigits());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }
    @Test
    void sendingPaymentFormWithLatinCharactersInYearField(){ //отправка формы c латинскими сиволами в поле год
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearWithLatinCharacters());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithCyrillicCharactersInYearField(){ //отправка формы c сиволами на кириллице в поле год
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearWithCyrillicCharacters());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithSpecialCharactersInYearField(){ //отправка формы со спецсиволами в поле год
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearWithSpecialCharacters());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithIeroglyphicsInYearField(){ //отправка формы с иероглифами в поле год
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearWithIeroglyphics());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithArabicScriptInYearField(){ //отправка формы с арабской вязью в поле год
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getYearWithArabicScript());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    //Заполнение поля Владелец валидными данными
    @Test
    void sendingPaymentFormWithCardHolderWithDoubleName(){ //отправка формы с двойным именем в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithDoubleName());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithCardHolderWithDoubleNameThroughDash(){ //отправка формы с двойным именем через тире в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithDoubleNameThroughDash());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithCardHolderWithLastNameWithAnApostrophe(){ //отправка формы с фамилией с апострофом в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithLastNameWithAnApostrophe());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithCardHolderWithNameInLowercase(){ //отправка формы с именем в нижнем регистре в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithNameInLowercase());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingPaymentFormWithCardHolderWithAnUppercaseName(){ //отправка формы с именем в верхнем регистре в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithAnUppercaseName());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyOkNotification("Операция одобрена Банком.");
    }
    //Заполнение поля Владелец НЕвалидными данными
    @Test
    void sendingPaymentFormWithCardHolderNameInCyrillic(){ //отправка формы с именем на кириллице в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameInCyrillic());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithCardHolderNameInChinese(){ //отправка формы с именем на китайском языке в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameInChinese());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithCardHolderNameInArabic(){ //отправка формы с именем на арабском языке в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameInArabic());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithCardHolderNameWithSpecialCharacters(){ //отправка формы со спецсимволами в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameWithSpecialCharacters());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithCardHolderNameInNumbers(){ //отправка формы с цифрами в поле Владелец
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameInNumbers());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    //Заполнение поля Код Карты НЕвалидными данными
    @Test
    void sendingPaymentFormWithCardCodeWithOneDigit(){ //отправка формы с кодом карточки с одной цифрой
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithOneDigit());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithCardCodeWithTwoDigit(){ //отправка формы с кодом карточки с двумя цифрами
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithTwoDigit());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingPaymentFormWithCardCodeWithAllZeros(){ //отправка формы с кодом карточки со всеми нулями
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithAllZeros());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithCardCodeWithLatinCharacters(){ //отправка формы с кодом карточки латинскими символами
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithLatinCharacters());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithCardCodeWithCyrillicCharacters(){ //отправка формы с кодом карточки символами на кириллице
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithCyrillicCharacters());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithCardCodeWithSpecialCharacters(){ //отправка формы с кодом карточки спецсимволами
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithSpecialCharacters());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithCardCodeWithIeroglyphics(){ //отправка формы с кодом карточки иероглифами
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithIeroglyphics());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingPaymentFormWithCardCodeWithArabicScript(){ //отправка формы с кодом карточки арабской вязью
        paymentPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        paymentPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        paymentPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        paymentPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        paymentPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithArabicScript());
        paymentPage.submittingForm();
        paymentPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
}
