package ru.secteam.teamwork.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import ru.secteam.teamwork.model.enums.Gender;
import ru.secteam.teamwork.model.enums.PetType;

import java.util.Objects;

/**
 * Класс для всех видов животных в приюте.
 * На данный момент это кошки и собаки.
 * Если приют будет расширяться, то это будет записываться в тип животного.
 */
@Data
@Entity (name = "Animals")
@JsonIgnoreProperties (value = "parent")
public class Animal {
    @Id
    @GeneratedValue
    private long id;
    private long chatId;
    private String name;
    private int ageMonth;
    private Gender gender;
    private PetType petType;
    @OneToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

}

