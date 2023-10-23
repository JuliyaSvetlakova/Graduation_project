package ru.netology.diploma.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.diploma.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private final SelenideElement buyButton = $(byText("Купить"));
    private final SelenideElement paymentHeading = $(byText("Оплата по карте"));
    private final SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $("[placeholder='08']");
    private final SelenideElement year = $("[placeholder='22']");
    private final SelenideElement holder = $$("input").get (3);
    private final SelenideElement cardCode = $("[placeholder='999']");
    private final SelenideElement continueButton = $(byText("Продолжить"));

    private final SelenideElement errorNotification = $(".notification_status_error .notification__content");
    private final SelenideElement okNotification  = $(".notification_status_ok .notification__content");
    private final SelenideElement errorInputInvalidCardNumber = $$(".input_invalid .input__sub").get (0);
    private final SelenideElement errorInputInvalidMonth =  $$(".input_invalid .input__sub").get (1);
    private final SelenideElement errorInputInvalidYear = $$(".input_invalid .input__sub").get (2);
    private final SelenideElement errorInputInvalidHolder = $$(".input_invalid .input__sub").get (3);
    private final SelenideElement errorInputInvalidCardCode = $$(".input_invalid .input__sub").get (4);
    private final SelenideElement errorInputInvalid = $(".input_invalid .input__sub");

    public void goToThePaymentPage() {
        buyButton.click();
        paymentHeading.shouldBe(visible);
    }
    public void fillingInTheCardNumberField(DataHelper.CardNumberInfo info) {
        cardNumber.setValue(info.getNumber());
    }
    public void fillingInTheMonthField(DataHelper.MonthInfo info) {
        month.setValue(info.getMonth());
    }
    public void fillingInTheYearField(DataHelper.YearInfo info) {
        year.setValue(info.getYear());
    }
    public void fillingInTheCardHolderField(DataHelper.CardHolderInfo info) {
        holder.setValue(info.getName());
    }
    public void fillingInTheCardCodeField(DataHelper.CardCodeInfo info) {
        cardCode.setValue(info.getCode());
    }
    public void submittingForm() {
        continueButton.click();
    }

    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText), Duration.ofSeconds(25)).shouldBe(visible);
    }
    public void verifyOkNotification(String expectedText) {
        okNotification.shouldHave(exactText(expectedText), Duration.ofSeconds(25)).shouldBe(visible);
    }
    public void verifyNotificationInputInvalidError(String expectedText) {
        errorInputInvalid .shouldHave(exactText(expectedText), Duration.ofSeconds(5)).shouldBe(visible);
    }
    public void verifyNotificationAboutCardNumberEntryError(String expectedText) {
        errorInputInvalidCardNumber.shouldHave(exactText(expectedText), Duration.ofSeconds(5)).shouldBe(visible);
    }
    public void verifyNotificationAboutMonthEntryError(String expectedText) {
        errorInputInvalidMonth.shouldHave(exactText(expectedText), Duration.ofSeconds(5)).shouldBe(visible);
    }
    public void verifyNotificationAboutYearEntryError(String expectedText) {
        errorInputInvalidYear.shouldHave(exactText(expectedText), Duration.ofSeconds(5)).shouldBe(visible);
    }
    public void verifyNotificationAboutHolderEntryError(String expectedText) {
        errorInputInvalidHolder.shouldHave(exactText(expectedText), Duration.ofSeconds(5)).shouldBe(visible);
    }
    public void verifyNotificationAboutCardCodeEntryError(String expectedText) {
        errorInputInvalidCardCode.shouldHave(exactText(expectedText), Duration.ofSeconds(5)).shouldBe(visible);
    }

}
