package ru.secteam.teamwork.model;

import lombok.Getter;
import lombok.Setter;
import ru.secteam.teamwork.model.enumClasses.Gender;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Objects;
/**
 * Сущность создания таблицы волонтеров в БД.
 */
@Getter
@Entity(name = "Volunteers")
public class Volunteer {
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
    private Integer chatId;
    @Getter
    @Setter
    private Shelter shelter;

    public Volunteer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return id == volunteer.id && gender == volunteer.gender && Objects.equals(name, volunteer.name) && Objects.equals(age, volunteer.age) && Objects.equals(chatId, volunteer.chatId) && Objects.equals(shelter, volunteer.shelter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, chatId, shelter);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", chatId=" + chatId +
                ", shelter=" + shelter +
                '}';
    }
}
