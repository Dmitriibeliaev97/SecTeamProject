package ru.secteam.teamwork.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import ru.secteam.teamwork.model.enums.Gender;

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
    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;


}
