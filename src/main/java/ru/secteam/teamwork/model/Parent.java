package ru.secteam.teamwork.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
/**
 * Сущность, создается на этапе взятия животного.
 * Наследует класс User + взятое животное
 */
@Getter
@Setter
public class Parent extends User{

    private Animal animal;


    public Parent(long id, long chatId, String firstName, String secondName, int age, boolean gender, String userName) {
        super(id, chatId, firstName, secondName, age, gender, userName);
    }
}
