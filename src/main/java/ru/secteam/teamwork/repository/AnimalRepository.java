package ru.secteam.teamwork.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Animal;

import java.util.Optional;

/**
 * Репозиторий для связи с таблицей животных из БД.
 */
public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
