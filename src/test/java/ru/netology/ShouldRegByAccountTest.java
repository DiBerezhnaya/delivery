package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class ShouldRegByAccountTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.headless = true;
        open("http://localhost:9999");
    }

    String deliveryDate = GenerateDate.generateDate(3);

    @Test
    void inputWithoutAutocompletion() {
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
        $("[placeholder='Город']").setValue("Ка");
        $x("//span[text()=\"Казань\"]").click();
        $("[data-test-id='date']").click();
        LocalDate dateDefault = LocalDate.now().plusDays(3);
        LocalDate dateOfMeeting = LocalDate.now().plusDays(7);
        String dayToSearch = String.valueOf(dateOfMeeting.getDayOfMonth());
        if (dateOfMeeting.getMonthValue() > dateDefault.getMonthValue() | dateOfMeeting.getYear() > dateDefault.getYear()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(dayToSearch)).click();
        $(byName("name")).val("Иван Петров");
        $("[name='phone']").val("+89005558844");
        $x("//span[@class='checkbox__box']").click();
        $(byText("Забронировать")).click();
        $("[class*='spin spin_size_m']").shouldBe(appear);
        $(withText("Успешно!"))
                .shouldBe(appear, Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    public static class GenerateDate {
        public static String generateDate(int days) {
            return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("12.08.2022"));
        }
    }
}


