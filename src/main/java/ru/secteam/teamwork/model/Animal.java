package ru.secteam.teamwork.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Класс для всех видов животных в приюте.
 * На данный момент это кошки и собаки.
 * Если приют будет расширяться, то это будет записываться в тип животного.
 */
@Entity
@Getter
@Setter
public class Animal {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int age;
    private boolean gender;
    private String shelter;
    private String typePet;
    private Parent parent;

    public Animal(long id, String name, int age, boolean gender, String shelter, String typePet, Parent parent) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.shelter = shelter;
        this.typePet = typePet;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && age == animal.age && gender == animal.gender && Objects.equals(name, animal.name) && Objects.equals(shelter, animal.shelter) && Objects.equals(typePet, animal.typePet) && Objects.equals(parent, animal.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, shelter, typePet, parent);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", shelter='" + shelter + '\'' +
                ", typePet='" + typePet + '\'' +
                ", parent=" + parent +
                '}';
    }
}

