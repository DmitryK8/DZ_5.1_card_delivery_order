package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;
import ru.netology.delivery.data.RegistrationByCardInfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;
import static org.openqa.selenium.Keys.CONTROL;

public class DeliveryTest {


    @Test
    void shouldChangeTheDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        RegistrationByCardInfo info = DataGenerator.Registration.generateByCard("ru");
        String firstDate = generateDate(12);
        String changeTheDate = generateDate(17);


        $("[data-test-id='city'] input").setValue(info.getCity());
        $ ( " [data-test-id = 'date'] input" ).doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue(info.getPhone());
        $("[data-test-id='agreement']").click();
        $$(".button__text").find(exactText("Запланировать")).click();

        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(15));

        //удаление строк
        $("[data-test-id='city'] input").sendKeys(CONTROL + "a");
        $("[data-test-id='city'] input").sendKeys(BACK_SPACE);
        $ ( " [data-test-id = 'date'] input" ).doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id='name'] input").sendKeys(CONTROL + "a");
        $("[data-test-id='name'] input").sendKeys(BACK_SPACE);
        $("[data-test-id='phone'] input").sendKeys(CONTROL + "a");
        $("[data-test-id='phone'] input").sendKeys(BACK_SPACE);
        $("[data-test-id='agreement']").click();

        //повторный ввод
        $("[data-test-id='city'] input").setValue(info.getCity());
        $("[data-test-id='date'] input").setValue(changeTheDate);
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue(info.getPhone());
        $("[data-test-id='agreement']").click();
        $$(".button__text").find(exactText("Запланировать")).click();

        $("[data-test-id='success-notification']")
                .shouldBe(visible).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        $("[data-test-id='success-notification']").shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(15));


    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
