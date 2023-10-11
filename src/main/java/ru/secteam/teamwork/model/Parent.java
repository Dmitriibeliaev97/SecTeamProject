package ru.secteam.teamwork.model;

import lombok.Getter;
import lombok.Setter;
import ru.secteam.teamwork.model.enumClasses.Gender;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
/**
 * Сущность создания таблицы усыновителей в БД.
 */
@Entity(name = "Parents")
public class Parent {
    @Id
    @GeneratedValue
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer age;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private Gender gender;
    @Getter
    @Setter
    private Animal animal;

    public Parent() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parent parent = (Parent) o;
        return id == parent.id && gender == parent.gender && Objects.equals(name, parent.name) && Objects.equals(age, parent.age) && Objects.equals(username, parent.username) && Objects.equals(animal, parent.animal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, username, gender, animal);
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", gender=" + gender +
                ", animal=" + animal +
                '}';
    }
}
