package ru.secteam.teamwork.model;

import lombok.Getter;
import lombok.Setter;
import ru.secteam.teamwork.model.enumClasses.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Сущность создания таблицы приютов в БД.
 */
@Getter
@Entity(name = "Shelters")
public class Shelter {
    @Id
    @GeneratedValue
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String information;
    @Getter
    @Setter
    private String instruction;
    @Getter
    @Setter
    private Type typeOfAnimals;

    public Shelter() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return id == shelter.id && Objects.equals(name, shelter.name) && Objects.equals(address, shelter.address) && Objects.equals(information, shelter.information) && Objects.equals(instruction, shelter.instruction) && typeOfAnimals == shelter.typeOfAnimals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, information, instruction, typeOfAnimals);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", information='" + information + '\'' +
                ", instruction='" + instruction + '\'' +
                ", typeOfAnimals=" + typeOfAnimals +
                '}';
    }
}
