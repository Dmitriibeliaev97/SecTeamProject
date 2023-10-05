package ru.secteam.teamwork.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Класс родитель для всех видов животных в приюте.
 * На данный момент это кошки и собаки.
 * Если приют будет расширяться, то сущности новых животных будут наследовать этот класс.
 */
public class Animal {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int age;
    private boolean gender;
    private String shelter;

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", shelter='" + shelter + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && age == animal.age && gender == animal.gender && Objects.equals(name, animal.name) && Objects.equals(shelter, animal.shelter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, shelter);
    }

    public Animal(long id, String name, int age, boolean gender, String shelter) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.shelter = shelter;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setShelter(String shelter) {
        this.shelter = shelter;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isGender() {
        return gender;
    }

    public String getShelter() {
        return shelter;
    }
}
