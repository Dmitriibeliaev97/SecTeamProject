package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.model.Volunteer;

import java.util.List;
/**
 * Репозиторий для связи с таблицей волонтера из БД.
 */
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    /**
     * Поиск волонтера по chatID.
     * ChatID индивидуальный для каждого пользователя бота.
     * @param chatId
     * @return
     */
    Volunteer findByChatId(Long chatId);
    /**
     * Удаление волонтера по chatID.
     * ChatID индивидуальный для каждого пользователя бота.
     * @param chatId
     * @return
     */
    void deleteByChatId(Long chatId);

    /**
     * Поиск волонтеров по приюту.
     * Выдается список волонтеров, так как у приюта их может быть несколько.
     * @param shelter
     * @return
     */
    List<Volunteer> findVolunteersByShelter(Shelter shelter);
}
