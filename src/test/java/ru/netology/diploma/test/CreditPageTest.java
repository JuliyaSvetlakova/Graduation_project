package ru.netology.diploma.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.page.CreditPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditPageTest {
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
    @Test
    void sendingCreditFormWithAllValidData(){ //отправка формы со Всеми Действительными Данными
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithCardNumberInTheRejectedStatus(){ //отправка формы c номером карты в статусе отклонено
        creditPage.fillingInTheCardNumberField(DataHelper.getSecondCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyErrorNotification("Ошибка! Банк отказал в проведении операции.");//
    }
    @Test
    void sendingCreditBlankForm(){ //отправка незаполненной формы
        creditPage.submittingForm();

        creditPage.verifyNotificationAboutCardNumberEntryError("Неверный формат");
        creditPage.verifyNotificationAboutMonthEntryError("Неверный формат");
        creditPage.verifyNotificationAboutYearEntryError("Неверный формат");
        creditPage.verifyNotificationAboutHolderEntryError("Поле обязательно для заполнения");
        creditPage.verifyNotificationAboutCardCodeEntryError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithBlankCardNumberData(){ //отправка формы с незаполненными Данными номера карты
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithBlankMonthData(){ //отправка формы с незаполненными Данными Месяца
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithBlankYearData(){ //отправка формы с незаполненными Данными Года
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithBlankHolderData(){ //отправка формы с незаполненными Данными Владельца
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Поле обязательно для заполнения");
    }
    @Test
    void sendingCreditFormWithBlankCardCodeData(){ //отправка формы с незаполненными Данными кода карты
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    //Заполнение поля номер карты невалидными данными
    @Test
    void sendingCreditFormWithFifteenDigitsInCardNumberField(){ //отправка формы c 15 цифрами в поле номер карты
        creditPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithFifteenDigits());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithSeventeenDigitsInCardNumberField(){ //отправка формы c 17 цифрами в поле номер карты
        creditPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithSeventeenDigits());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyErrorNotification("Ошибка! Банк отказал в проведении операции.");
    }
    @Test
    void sendingCreditFormWithAllZerosInCardNumberField(){ //отправка формы cо всеми нулями в поле номер карты
        creditPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithAllZeros());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithLatinCharactersInCardNumberField(){ //отправка формы c латинскими сиволами в поле номер карты
        creditPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithLatinCharacters());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithCyrillicCharactersInCardNumberField(){ //отправка формы c символами на кириллице в поле номер карты
        creditPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithCyrillicCharacters());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithSpecialCharactersInCardNumberField(){ //отправка формы со спецсимволами в поле номер карты
        creditPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithSpecialCharacters());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithIeroglyphicsInCardNumberField(){ //отправка формы с иероглифами в поле номер карты
        creditPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithIeroglyphics());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithArabicScriptInCardNumberField(){ //отправка формы с арабской вязью в поле номер карты
        creditPage.fillingInTheCardNumberField(DataHelper.getCardNumberWithArabicScript());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }

    //Срок действия карты
    @Test
    void sendingCreditFormWithAnExpiredCardForMonth(){ //отправка формы с истеченным сроком действия карты на месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getLastMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getCurrentYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }
    @Test
    void sendingCreditFormWithTheExpirationOfTheCardInTheCurrentMonth(){ //отправка формы с истеченнмем срока действия карты в текущем месяце
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getCurrentYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithTheExpirationOfTheCardInTheNextMonth(){ //отправка формы с истеченнмем срока действия карты в следующем месяце
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getNextMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getCurrentYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithAnExpiredCardForYear(){ //отправка формы с истеченным сроком действия карты на год
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getLastYear());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Истёк срок действия карты");
    }
    @Test
    void sendingCreditFormWithTheExpirationDateOfTheCardNextYear(){ //отправка формы с истечением срока действия карты в следующем году
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithDataIncludedInTheFiveYearValidityPeriodOfTheCardLessThanMonth(){ //отправка формы с данными входящими в пятилетний срок действия карты, меньше на месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getLastMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearBeforeTheCardExpires());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithDataIncludedInTheFiveYearValidityPeriodOfTheCard(){ //отправка формы с данными входящими в пятилетний срок действия карты
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearBeforeTheCardExpires());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithDataExceedingTheFiveYearValidityPeriodOfTheCardForMonth(){ //отправка формы с данными превышающими пятилетний срок действия карты на месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getNextMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearBeforeTheCardExpires());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");//
    }
    @Test
    void sendingCreditFormWithDataExceedingTheFiveYearValidityPeriodOfTheCardForYear(){ //отправка формы с данными превышающими пятилетний срок действия карты на год
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getCurrentMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getExpirationYearOfTheCard());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }

    //Заполнение поля месяц невалидными данными
    @Test
    void sendingCreditFormWithSingleDigitInTheMonthField(){ //отправка формы с одной цифрой в поле месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthWithOneDigit());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithThreeDigitsInTheMonthField(){ //отправка формы с тремя цифрами в поле месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthWithThreeDigits());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }
    @Test
    void sendingCreditFormWithAllZerosInTheMonthField(){ //отправка формы со всеми нолями в поле месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthWithAllZeros());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");//
    }
    @Test
    void sendingCreditFormWithTheThirteenthMonthInTheMonthField(){ //отправка формы с тринадцатым месяцем в поле месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getThirteenthMonth());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }
    @Test
    void sendingCreditFormWithLatinCharactersInMonthField(){ //отправка формы c латинскими сиволами в поле месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthWithLatinCharacters());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithCyrillicCharactersInMonthField(){ //отправка формы c сиволами на кириллице в поле месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthWithCyrillicCharacters());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithSpecialCharactersInMonthField(){ //отправка формы со спецсиволами в поле месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthWithSpecialCharacters());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithIeroglyphicsInMonthField(){ //отправка формы с иероглифами в поле месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthWithIeroglyphics());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithArabicScriptInMonthField(){ //отправка формы с арабской вязью в поле месяц
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthWithArabicScript());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    //Заполнение поля год невалидными данными
    @Test
    void sendingCreditFormWithSingleDigitInTheYearField(){ //отправка формы с одной цифрой в поле год
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearWithOneDigit());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithThreeDigitsInTheYearField(){ //отправка формы с тремя цифрами в поле год
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearWithThreeDigits());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверно указан срок действия карты");
    }
    @Test
    void sendingCreditFormWithLatinCharactersInYearField(){ //отправка формы c латинскими сиволами в поле год
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearWithLatinCharacters());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithCyrillicCharactersInYearField(){ //отправка формы c сиволами на кириллице в поле год
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearWithCyrillicCharacters());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithSpecialCharactersInYearField(){ //отправка формы со спецсиволами в поле год
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearWithSpecialCharacters());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithIeroglyphicsInYearField(){ //отправка формы с иероглифами в поле год
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearWithIeroglyphics());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithArabicScriptInYearField(){ //отправка формы с арабской вязью в поле год
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getYearWithArabicScript());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    //Заполнение поля Владелец валидными данными
    @Test
    void sendingCreditFormWithCardHolderWithDoubleName(){ //отправка формы с двойным именем в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithDoubleName());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithCardHolderWithDoubleNameThroughDash(){ //отправка формы с двойным именем через тире в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithDoubleNameThroughDash());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithCardHolderWithLastNameWithAnApostrophe(){ //отправка формы с фамилией с апострофом в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithLastNameWithAnApostrophe());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithCardHolderWithNameInLowercase(){ //отправка формы с именем в нижнем регистре в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithNameInLowercase());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    @Test
    void sendingCreditFormWithCardHolderWithAnUppercaseName(){ //отправка формы с именем в верхнем регистре в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderWithAnUppercaseName());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyOkNotification("Операция одобрена Банком.");
    }
    //Заполнение поля Владелец НЕвалидными данными
    @Test
    void sendingCreditFormWithCardHolderNameInCyrillic(){ //отправка формы с именем на кириллице в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameInCyrillic());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithCardHolderNameInChinese(){ //отправка формы с именем на китайском языке в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameInChinese());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithCardHolderNameInArabic(){ //отправка формы с именем на арабском языке в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameInArabic());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithCardHolderNameWithSpecialCharacters(){ //отправка формы со спецсимволами в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameWithSpecialCharacters());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithCardHolderNameInNumbers(){ //отправка формы с цифрами в поле Владелец
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderNameInNumbers());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeInfo());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    //Заполнение поля Код Карты НЕвалидными данными
    @Test
    void sendingCreditFormWithCardCodeWithOneDigit(){ //отправка формы с кодом карточки с одной цифрой
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithOneDigit());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithCardCodeWithTwoDigit(){ //отправка формы с кодом карточки с двумя цифрами
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithTwoDigit());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");
    }
    @Test
    void sendingCreditFormWithCardCodeWithAllZeros(){ //отправка формы с кодом карточки со всеми нулями
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithAllZeros());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithCardCodeWithLatinCharacters(){ //отправка формы с кодом карточки латинскими символами
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithLatinCharacters());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithCardCodeWithCyrillicCharacters(){ //отправка формы с кодом карточки символами на кириллице
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithCyrillicCharacters());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithCardCodeWithSpecialCharacters(){ //отправка формы с кодом карточки спецсимволами
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithSpecialCharacters());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithCardCodeWithIeroglyphics(){ //отправка формы с кодом карточки иероглифами
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithIeroglyphics());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }
    @Test
    void sendingCreditFormWithCardCodeWithArabicScript(){ //отправка формы с кодом карточки арабской вязью
        creditPage.fillingInTheCardNumberField(DataHelper.getFirstCardNumberInfo());
        creditPage.fillingInTheMonthField(DataHelper.getMonthInfo());
        creditPage.fillingInTheYearField(DataHelper.getNextYearInfo());
        creditPage.fillingInTheCardHolderField(DataHelper.getCardHolderInfo());
        creditPage.fillingInTheCardCodeField(DataHelper.getCardCodeWithArabicScript());
        creditPage.submittingForm();
        creditPage.verifyNotificationInputInvalidError("Неверный формат");//
    }

}
