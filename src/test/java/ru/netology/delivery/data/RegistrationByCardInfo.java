package ru.netology.delivery.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RegistrationByCardInfo {
    private final String city;
    private final LocalDate cardExpire;
    private final String name;
    private final String phone;
}