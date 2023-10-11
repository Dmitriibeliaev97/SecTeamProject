package ru.secteam.teamwork.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import ru.secteam.teamwork.model.enums.Gender;
import ru.secteam.teamwork.model.enums.Shelter;

import javax.persistence.*;
import java.util.Objects;
/**
 * Сущность создания таблицы волонтеров в БД.
 * Создается либо кнопкой хочу стать волонтером, либо через сваггер.
 */
@Getter
@Setter
@Entity(name = "Volunteers")
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
    @JoinColumn(name = "shelter_id")
    @JsonBackReference
    private Shelter shelter;

    public Volunteer(long id, String name, String userName, int age, Gender gender, long chatId, Shelter shelter) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.chatId = chatId;
        this.shelter = shelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return id == volunteer.id && age == volunteer.age && gender == volunteer.gender && chatId == volunteer.chatId && Objects.equals(name, volunteer.name) && Objects.equals(userName, volunteer.userName) && Objects.equals(shelter, volunteer.shelter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userName, age, gender, chatId, shelter);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", chatId=" + chatId +
                ", shelter='" + shelter + '\'' +
                '}';
    }
}
