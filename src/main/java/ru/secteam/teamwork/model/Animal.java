package ru.secteam.teamwork.model;

import lombok.Getter;
import lombok.Setter;
import ru.secteam.teamwork.model.enumClasses.Gender;
import ru.secteam.teamwork.model.enumClasses.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Класс родитель для всех видов животных в приюте.
 * На данный момент это кошки и собаки.
 */
@Entity(name = "Animals")
public class Animal {
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
    private Gender gender;
    @Getter
    @Setter
    private Type type;

    public Animal() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && age == animal.age && gender == animal.gender && Objects.equals(name, animal.name) && Objects.equals(type, animal.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, type);
    }


    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", type='" + type + '\'' +
                '}';
    }
}
