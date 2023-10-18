package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.model.enums.PetType;
/**
 * Репозиторий для связи с таблицей приюта из БД.
 */
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    /**
     * Поиск приюта по типу животного.
     * @param petType
     * @return
     */
    Shelter findByPetType(PetType petType);
}
