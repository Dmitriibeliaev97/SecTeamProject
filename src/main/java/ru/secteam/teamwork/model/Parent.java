package ru.secteam.teamwork.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.secteam.teamwork.model.enums.ButtonSelection;
import ru.secteam.teamwork.model.enums.Gender;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Модель создаёт табдицу всех усыновителей со всей основновной иформации о нём.
 * Ячейка в таблице создается для любого пользователя, запустившего бот.
 * В таком случае в таблицу заносится информация по chat id, user name.
 * После этого пользователь выбирает приюот и эта информаиця попадает в button selection.
 * Остальная информация в таблицу заносится волонтером через веб-приложение, когда пользователь передал свои данные.
 * К каждому усыновителю на время испытательного срока привязывается только одно животное
 * После прохождения испытательного срока усыновитель может взять новое животное
 * За идентификатор берется chat ID, так как у каждого пользователя он индивидуальный
 */
@Data
@Entity(name = "Parents")
//@JsonIgnoreProperties(value = "animal")
public class Parent {
    @Id
    private long chatId;
    private Integer age;
    private String name;
    private Gender gender;
    private String userName;
    private LocalDate dateOfAdoption;
    private LocalDate dateOfFinishAdoption;
    private ButtonSelection buttonSelection;
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "animal_id")
    @JsonBackReference
    private Animal animal;
    private LocalDate report;

    public Parent(String name, Integer age, Gender gender, LocalDate dateOfAdoption, Animal animal) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.dateOfAdoption = dateOfAdoption;
        this.animal = animal;
    }

    public Parent() {
    }

}
