package ru.secteam.teamwork.model.shelters;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Volunteer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;


/**
 * Сущность создания таблицы приюта кошек в БД.
 */
@Getter
@Setter
@Entity
public class CatsShelter {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(mappedBy = "shelter")
    @JsonManagedReference
    private Collection<Animal> cats;
    @OneToMany(mappedBy = "shelter")
    @JsonManagedReference
    private Volunteer volunteer;

}
