package ru.secteam.teamwork.model;


import lombok.Getter;
import lombok.Setter;
import ru.secteam.teamwork.model.enums.Gender;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
/**
 * Сущность создания таблицы всех потенциальных усыновителей, нажавших кнопку "хочу стать усыновителем"  в БД.
 */
@Entity(name = "Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private long id;
    private long chatId;
    private String name;
    private int age;
    private Gender gender;
    private String userName;

    public User(long id, long chatId, String name, int age, Gender gender, String userName) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && chatId == user.chatId && age == user.age && gender == user.gender && Objects.equals(name, user.name) && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, age, gender, userName);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", userName='" + userName + '\'' +
                '}';
    }
}
