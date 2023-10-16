package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.model.Volunteer;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer findByChatId(Long chatId);

    void deleteByChatId(Long chatId);

    Volunteer findByShelter(Shelter shelter);

    List<Volunteer> findVolunteersByShelter(Shelter shelter);
}
