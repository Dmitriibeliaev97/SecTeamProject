package ru.secteam.teamwork.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.sql.Timestamp;
import java.util.Objects;
/**
 * Сущность создания таблицы всех потенциальных усыновителей, нажавших кнопку "хочу стать усыновителем"  в БД.
 */
@Entity (name = "Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private long id;
    private long chatId;
    private String firstName;
    private String secondName;
    private int age;
    private boolean gender;
    private String userName;

    public User(long id, long chatId, String firstName, String secondName, int age, boolean gender, String userName) {
        this.id = id;
        this.chatId = chatId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.gender = gender;
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && chatId == user.chatId && age == user.age && Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) && Objects.equals(gender, user.gender) && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, firstName, secondName, age, gender, userName);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
