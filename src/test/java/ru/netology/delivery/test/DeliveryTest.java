package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;
import ru.netology.delivery.data.RegistrationByCardInfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class DeliveryTest {


    @Test
    void shouldChangeTheDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        RegistrationByCardInfo info = DataGenerator.Registration.generateByCard("ru");
        String firstDate = info.getCardExpire().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String changeTheDate = generateDate(50);

        $("[data-test-id='city']").setValue(info.getCity());
        $ ( " [data-test-id = 'date'] input" ).doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue(info.getPhone());
        $("[data-test-id='agreement']").click();
        $$(".button__text").find(exactText("Запланировать")).click();

        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(15));



    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
