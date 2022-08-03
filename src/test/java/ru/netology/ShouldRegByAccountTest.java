package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class ShouldRegByAccountTest {

    String deliveryDate = GenerateDate.generateDate(3);

    @Test
    void inputWithoutAutocompletion() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        $("[placeholder='Город']").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(deliveryDate);
        $(byName("name")).val("Иван Петров");
        $("[name='phone']").val("+89005558844");
        $x("//span[@class='checkbox__box']").click();
        $(byText("Забронировать")).click();
        $("[class*='spin spin_size_m']").shouldBe(appear);
        $(withText("Успешно!"))
                .shouldBe(appear, Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void autocompleteInput() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        $("[placeholder='Город']").setValue("Ка");
        $x("//span[text()=\"Казань\"]").click();
//        //$("[type='date']").click();
//      //  $("[class='calendar__title']").click();
//        ElementsCollection calendarDay = $$("[class='calendar__layout']");
//        calendarDay.shouldBe("data-day=\"1660251600000\""));
////       // $$("[class='calendar__day']").filter(visible).shouldBe("1659646800000").click();
//     //   $x("//table[@class='calendar__layout']").click();
//        //$x("//*[@data-day=\"1659646800000\"]").click();
//        $(byName("name")).val("Иван Петров");
//        $("[name='phone']").val("+89005558844");
    }

    public static class GenerateDate {
        public static String generateDate(int days) {
            return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("06.08.2022"));
        }
    }
}


