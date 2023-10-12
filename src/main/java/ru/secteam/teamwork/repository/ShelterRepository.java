package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Shelter;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
