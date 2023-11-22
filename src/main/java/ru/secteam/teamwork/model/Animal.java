package ru.secteam.teamwork.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.secteam.teamwork.model.enums.Gender;
import ru.secteam.teamwork.model.enums.PetType;

import java.util.Objects;

/**
 * Класс для всех видов животных в приюте.
 * На данный момент это кошки и собаки.
 * Если животное усыновили, то на время испытательного срок усыновителя у животно появляется поле с усыновителем
 * После прохождения испытательного срока животное удаляется из таблицы
 */
@Data
@Entity(name = "Animals")
@JsonIgnoreProperties(value = "parent")
public class Animal {
    @Id
    @GeneratedValue
    private long id;
    private Integer ageMonth;
    private String name;
    private Gender gender;
    private PetType petType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_chatId")
    @JsonManagedReference
    private Parent parent;
}

