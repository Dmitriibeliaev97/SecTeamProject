package ru.secteam.teamwork.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Getter;
import lombok.Setter;
import ru.secteam.teamwork.model.enums.Gender;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * Сущность, создается на этапе взятия животного.
 * Наследует класс User + взятое животное
 */
@Getter
@Setter
@Entity(name = "Parents")
@JsonIgnoreProperties(value = {"animals"})
public class Parent extends User{
    @OneToMany(mappedBy = "parent")
    private Collection<Animal> animals;


    public Parent(long id, long chatId, String name, int age, Gender gender, String userName) {
        super(id, chatId, name, age, gender, userName);
    }
}
