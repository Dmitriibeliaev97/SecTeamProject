package ru.secteam.teamwork.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Objects;
/**
 * Сущность создания таблицы волонтеров в БД.
 * Поля таблицы:
 * 1. id(идентификатор);
 * 2. Имя ;
 * 3. Приют, к которому прикреплен волонтер.
 */
@Entity(name = "Volunteers")
public class Volunteer {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String shelter;

    public Volunteer(long id, String name, String shelter) {
        this.id = id;
        this.name = name;
        this.shelter = shelter;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShelter(String shelter) {
        this.shelter = shelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return id == volunteer.id && Objects.equals(name, volunteer.name) && Objects.equals(shelter, volunteer.shelter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shelter);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shelter='" + shelter + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShelter() {
        return shelter;
    }
}
