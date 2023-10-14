package ru.secteam.teamwork.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import ru.secteam.teamwork.model.enums.Gender;

import java.util.Objects;
/**
 * Сущность создания таблицы волонтеров в БД.
 * Волонтер заносится в БД через свагер.
 * Заявку стать волонтером можно переать через бот и передать всю информацию о себе
 * У каждого волонтера свой индивидуальный chat ID, поэтому он является идентификатором
 * Волонтер привязывается к одному приюту.
 * При этом волонтеров и приюта может быть несколько
 */
@Data
@Entity(name = "Volunteers")
@JsonIgnoreProperties(value = "shelter")
public class Volunteer {
    @Id
    private long chatId;
    private Integer age;
    private String name;
    private String userName;
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "shelter_name")
    private Shelter shelter;

    public Volunteer(String name, String userName, int age, Gender gender, Shelter shelter) {
        this.name = name;
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.shelter = shelter;
    }
}
