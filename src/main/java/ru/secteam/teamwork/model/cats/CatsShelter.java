package ru.secteam.teamwork.model.cats;

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
public class CatsShelter {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(mappedBy = "shelter")
    @JsonManagedReference
    private Collection<Cat> cats;

    public CatsShelter(long id, String name, Collection<Cat> cats) {
        this.id = id;
        this.name = name;
        this.cats = cats;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCats(Collection<Cat> cats) {
        this.cats = cats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatsShelter that = (CatsShelter) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(cats, that.cats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cats);
    }

    @Override
    public String toString() {
        return "CatsShelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cats=" + cats +
                '}';
    }
}
