package ru.secteam.teamwork.model.dogs;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Objects;

@Getter
@Entity
public class DogsShelter {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(mappedBy = "shelter")
    @JsonManagedReference
    private Collection<Dog> dogs;

    public DogsShelter(long id, String name, Collection<Dog> dogs) {
        this.id = id;
        this.name = name;
        this.dogs = dogs;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDogs(Collection<Dog> dogs) {
        this.dogs = dogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogsShelter that = (DogsShelter) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(dogs, that.dogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dogs);
    }

    @Override
    public String toString() {
        return "DogsShelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dogs=" + dogs +
                '}';
    }
}