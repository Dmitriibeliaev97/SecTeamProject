package ru.secteam.teamwork.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import ru.secteam.teamwork.model.enums.Gender;
import ru.secteam.teamwork.model.enums.Shelter;


import javax.persistence.*;
import java.util.Objects;

/**
 * Класс для всех видов животных в приюте.
 * На данный момент это кошки и собаки.
 * Если приют будет расширяться, то это будет записываться в тип животного.
 */
@Getter
@Setter
@Entity
public class Animal {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int ageMonth;
    private Gender gender;
    private Shelter shelter;
    public enum TypePet {DOG, CAT}
    private TypePet typePet;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Parent parent;

    public Animal(long id, String name, int ageMonth, Gender gender, Shelter shelter, TypePet typePet, Parent parent) {
        this.id = id;
        this.name = name;
        this.ageMonth = ageMonth;
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
        return id == animal.id && ageMonth == animal.ageMonth && gender == animal.gender && Objects.equals(name, animal.name) && Objects.equals(shelter, animal.shelter) && typePet == animal.typePet && Objects.equals(parent, animal.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ageMonth, gender, shelter, typePet, parent);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ageMonth=" + ageMonth +
                ", gender=" + gender +
                ", shelter='" + shelter + '\'' +
                ", typePet=" + typePet +
                ", parent=" + parent +
                '}';
    }
}

