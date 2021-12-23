package ru.netology.delivery.test;

import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;
import ru.netology.delivery.data.RegistrationByCardInfo;

import static com.codeborne.selenide.Selenide.open;

public class DeliveryTest {

    @Test
    void shouldChangeTheDate() {
        open("http://localhost:9999");
        RegistrationByCardInfo info = DataGenerator.Registration.generateByCard("ru");

        System.out.println(info);
    }

}
