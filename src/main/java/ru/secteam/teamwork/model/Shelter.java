package ru.secteam.teamwork.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import ru.secteam.teamwork.model.enums.PetType;

import java.util.Collection;

/**
 * Модель создает табллицу со всеми приютами
 * Имеет в себе информацию о приюте и иструкцию к животному по типу приюта
 * К кажому приюту привязывается один или несколько волнтеров
 */

@Data
@Entity(name = "Shelters")
@JsonIgnoreProperties(value = "volunteers")
public class Shelter {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String address;
    private String info;
    private String instruction;
    private PetType petType;
    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
//    @JoinColumn(name = "volunteer_chatId")
    @JsonManagedReference
    private Collection<Volunteer> volunteers;

}
