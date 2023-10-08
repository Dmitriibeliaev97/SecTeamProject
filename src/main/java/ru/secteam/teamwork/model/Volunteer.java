package ru.secteam.teamwork.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    private boolean gender;
    private long chatId;
    private String shelter;

    public Volunteer(long id, String name, String userName, int age, boolean gender, long chatId, String shelter) {
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
