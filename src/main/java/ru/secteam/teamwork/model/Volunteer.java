package ru.secteam.teamwork.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import ru.secteam.teamwork.model.enums.Gender;

import java.util.Objects;
/**
 * Сущность создания таблицы волонтеров в БД.
 * Создается либо кнопкой хочу стать волонтером, либо через сваггер.
 */
@Data
@Entity(name = "Volunteers")
@JsonIgnoreProperties(value = "shelter")
public class Volunteer {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String userName;
    private int age;
    private Gender gender;
    private long chatId;
    @ManyToOne
    @JoinColumn(name = "shelter_name")
    private Shelter shelter;

}
