package ru.netology.diploma.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    //Валидные данные карты
    public static CardNumberInfo getFirstCardNumberInfo() {
        return new CardNumberInfo("4444 4444 4444 4441");
    }

    public static CardNumberInfo getSecondCardNumberInfo() {
        return new CardNumberInfo("4444 4444 4444 4442");
    }

    //НЕвалидные данные карты
    public static CardNumberInfo getCardNumberWithFifteenDigits() {
        Faker faker = new Faker();
        return new CardNumberInfo(faker.numerify("#### #### #### ###"));
    }

    public static CardNumberInfo getCardNumberWithSeventeenDigits() {
        Faker faker = new Faker();
        return new CardNumberInfo(faker.numerify("#### #### #### #### #"));
    }

    public static CardNumberInfo getCardNumberWithAllZeros() {
        return new CardNumberInfo("0000 0000 0000 0000");
    }

    public static CardNumberInfo getCardNumberWithLatinCharacters() {
        return new CardNumberInfo("ABCD EFGH IJKL MNOP");
    }

    public static CardNumberInfo getCardNumberWithCyrillicCharacters() {
        return new CardNumberInfo("АБВ ГДЕЖ ЗИКЛ МНОР");
    }

    public static CardNumberInfo getCardNumberWithSpecialCharacters() {
        return new CardNumberInfo("!$#* /&@ !$#* /&@");
    }

    public static CardNumberInfo getCardNumberWithIeroglyphics() {
        return new CardNumberInfo("名字 名字 名字 名字");
    }

    public static CardNumberInfo getCardNumberWithArabicScript() {
        return new CardNumberInfo("اسم اسم اسم اسم");
    }

    @Value
    public static class CardNumberInfo {
        String number;
    }

    // Валидные данные месяца

    public static MonthInfo getMonthInfo() {
        var month = new String[]{"01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"};
        return new MonthInfo(month[new Random().nextInt(month.length)]);
    }

    public static MonthInfo getLastMonthInfo() {
        return new MonthInfo(LocalDate.now().minusMonths(1)
                .format(DateTimeFormatter.ofPattern("MM")));
    }

    public static MonthInfo getCurrentMonthInfo() {
        return new MonthInfo(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
    }

    public static MonthInfo getNextMonthInfo() {
        return new MonthInfo(LocalDate.now().plusMonths(1)
                .format(DateTimeFormatter.ofPattern("MM")));
    }

    //НЕвалидные данные месяц
    public static MonthInfo getMonthWithOneDigit() {
        Faker faker = new Faker();
        return new MonthInfo(faker.numerify("#"));
    }

    public static MonthInfo getMonthWithThreeDigits() {
        return new MonthInfo("145");
    }

    public static MonthInfo getMonthWithAllZeros() {
        return new MonthInfo("00");
    }

    public static MonthInfo getThirteenthMonth() {
        return new MonthInfo("13");
    }

    public static MonthInfo getMonthWithLatinCharacters() {
        return new MonthInfo("AB");
    }

    public static MonthInfo getMonthWithCyrillicCharacters() {
        return new MonthInfo("АБ");
    }

    public static MonthInfo getMonthWithSpecialCharacters() {
        return new MonthInfo("!$");
    }

    public static MonthInfo getMonthWithIeroglyphics() {
        return new MonthInfo("名字");
    }

    public static MonthInfo getMonthWithArabicScript() {
        return new MonthInfo(" اسم");
    }

    @Value
    public static class MonthInfo {
        String month;
    }

    //Валидные данные года

    public static YearInfo getCurrentYearInfo() {
        return new YearInfo(LocalDate.now().format(DateTimeFormatter.ofPattern("yy")));
    }

    public static YearInfo getNextYearInfo() {
        return new YearInfo(LocalDate.now().plusYears(1)
                .format(DateTimeFormatter.ofPattern("yy")));
    }

    public static YearInfo getYearBeforeTheCardExpires() {
        return new YearInfo(LocalDate.now().plusYears(5)
                .format(DateTimeFormatter.ofPattern("yy")));
    }

    //НЕвалидные данные года
//  год, меньше, чем текуший на 1 год
    public static YearInfo getLastYear() {
        return new YearInfo(LocalDate.now().minusYears(1)
                .format(DateTimeFormatter.ofPattern("yy")));
    }

    // больше, чем текущий + 5 лет
    public static YearInfo getExpirationYearOfTheCard() {
        return new YearInfo(LocalDate.now().plusYears(6)
                .format(DateTimeFormatter.ofPattern("yy")));
    }

    public static YearInfo getYearWithOneDigit() {
        Faker faker = new Faker();
        return new YearInfo(faker.numerify("#"));
    }

    public static YearInfo getYearWithThreeDigits() {
        return new YearInfo("999");
    }

    public static YearInfo getYearWithLatinCharacters() {
        return new YearInfo("AB");
    }

    public static YearInfo getYearWithCyrillicCharacters() {
        return new YearInfo("АБ");
    }

    public static YearInfo getYearWithSpecialCharacters() {
        return new YearInfo("!$");
    }

    public static YearInfo getYearWithIeroglyphics() {
        return new YearInfo("名字");
    }

    public static YearInfo getYearWithArabicScript() {
        return new YearInfo(" اسم");
    }

    @Value
    public static class YearInfo {
        String year;
    }

    // Валидные имена
    public static CardHolderInfo getCardHolderInfo() {
        Faker faker = new Faker(new Locale("en"));
        return new CardHolderInfo(faker.name()
                .lastName() + " " + faker.name().firstName());
    }

    public static CardHolderInfo getCardHolderWithDoubleName() {
        Faker faker = new Faker(new Locale("en"));
        return new CardHolderInfo(faker.name()
                .lastName() + " " + faker.name().firstName() + " " + faker.name().firstName());
    }

    public static CardHolderInfo getCardHolderWithDoubleNameThroughDash() {
        Faker faker = new Faker(new Locale("en"));
        return new CardHolderInfo(faker.name()
                .lastName() + " " + faker.name().firstName() + " - " + faker.name().firstName());
    }

    public static CardHolderInfo getCardHolderWithLastNameWithAnApostrophe() {
        return new CardHolderInfo("O’Brien Robert");
    }

    public static CardHolderInfo getCardHolderWithNameInLowercase() {
        return new CardHolderInfo("smith robert");
    }

    public static CardHolderInfo getCardHolderWithAnUppercaseName() {
        return new CardHolderInfo("THOMPSON DONALD");
    }
    // НЕвалидные имена

    public static CardHolderInfo getCardHolderNameInCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        return new CardHolderInfo(faker.name()
                .lastName() + " " + faker.name().firstName());
    }

    public static CardHolderInfo getCardHolderNameInChinese() {
        Faker faker = new Faker(new Locale("zh_CN"));
        return new CardHolderInfo(faker.name()
                .lastName() + " " + faker.name().firstName());
    }

    public static CardHolderInfo getCardHolderNameInArabic() {
        Faker faker = new Faker(new Locale("ar"));
        return new CardHolderInfo(faker.name()
                .lastName() + " " + faker.name().firstName());
    }

    public static CardHolderInfo getCardHolderNameWithSpecialCharacters() {
        return new CardHolderInfo("!#$%^&*() !#$%^&*()");
    }

    public static CardHolderInfo getCardHolderNameInNumbers() {
        return new CardHolderInfo("123456789 987654321");
    }

    @Value
    public static class CardHolderInfo {
        String name;
    }

    //Валидный код карты
    public static CardCodeInfo getCardCodeInfo() {
        return new CardCodeInfo("333");
    }

    //НЕвалидный код карты

    public static CardCodeInfo getCardCodeWithTwoDigit() {
        Faker faker = new Faker();
        return new CardCodeInfo(faker.numerify("##"));
    }

    public static CardCodeInfo getCardCodeWithOneDigit() {
        Faker faker = new Faker();
        return new CardCodeInfo(faker.numerify("#"));
    }

    public static CardCodeInfo getCardCodeWithAllZeros() {
        return new CardCodeInfo("000");
    }

    public static CardCodeInfo getCardCodeWithLatinCharacters() {
        return new CardCodeInfo("ABC");
    }

    public static CardCodeInfo getCardCodeWithCyrillicCharacters() {
        return new CardCodeInfo("АБВ");
    }

    public static CardCodeInfo getCardCodeWithSpecialCharacters() {
        return new CardCodeInfo("!$*");
    }

    public static CardCodeInfo getCardCodeWithIeroglyphics() {
        return new CardCodeInfo("名字");
    }

    public static CardCodeInfo getCardCodeWithArabicScript() {
        return new CardCodeInfo(" اسم");
    }

    @Value
    public static class CardCodeInfo {
        String code;
    }

}
