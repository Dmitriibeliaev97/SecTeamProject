package ru.secteam.teamwork.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import ru.secteam.teamwork.model.enums.ButtonSelection;
import ru.secteam.teamwork.model.enums.Gender;

import java.text.SimpleDateFormat;

/**
 * Модель создаёт табдицу всех усыновителей со всей основновной иформации о нём
 * В таблицу заносится волонтером через веб-приложение
 * К каждому усыновителю на время испытательного срока привязывается только одно животное
 * После прохождения испытательного срока усыновитель может взять новое животное
 */
@Data
@Entity (name = "Parents")
@JsonIgnoreProperties(value = "animal")
public class Parent {
    @Id
    @GeneratedValue
    private long id;
    private long chatId;
    private String name;
    private int age;
    private Gender gender;
    private String userName;
    private SimpleDateFormat dateOfAdoption;
    private ButtonSelection buttonSelection;
    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;


}
