package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.model.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer findByChatId(Long chatId);

    void deleteByChatId(Long chatId);
}
