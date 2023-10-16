package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.model.enums.PetType;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Shelter findByPetType(PetType petType);
}
