package ru.secteam.teamwork.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import ru.secteam.teamwork.model.enums.PetType;


@Data
@Entity (name = "Shelters")
public class Shelter {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String address;
    private String info;
    private String instruction;
    private PetType petType;

}
