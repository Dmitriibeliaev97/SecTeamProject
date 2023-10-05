package ru.secteam.teamwork.model;

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
    private long id;
    private String name;
    private Animal animal;

    public Parent(long id, String name, Animal animal) {
        this.id = id;
        this.name = name;
        this.animal = animal;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parent parent = (Parent) o;
        return id == parent.id && Objects.equals(name, parent.name) && Objects.equals(animal, parent.animal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, animal);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", animal=" + animal +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Animal getAnimal() {
        return animal;
    }
}
