package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;
import ru.netology.delivery.data.RegistrationByCardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class DeliveryTest {

    @Test
    void shouldChangeTheDate() {
        open("http://localhost:9999");
        RegistrationByCardInfo info = DataGenerator.Registration.generateInfo("ru");
        String firstDate = DataGenerator.generateDate(12);
        String changeTheDate = DataGenerator.generateDate(17);


        $("[data-test-id='city'] input").setValue(info.getCity());
        $(" [data-test-id = 'date'] input").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue(info.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();

        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(15));
        $(".notification__content").click();
        //удаление строки дата
        $(" [data-test-id = 'date'] input").doubleClick().sendKeys(BACK_SPACE);
        //ввод новой даты
        $("[data-test-id='date'] input").setValue(changeTheDate);
        $(".button__text").click();
        $("[data-test-id=\"replan-notification\"]").shouldBe(Condition.text("Необходимо подтверждение"));
        $("[data-test-id=\"replan-notification\"]").click();
        $("[data-test-id=\"success-notification\"] ").shouldHave(Condition.text("Успешно!\n" +
                "Встреча успешно запланирована на " + changeTheDate));
    }
}
