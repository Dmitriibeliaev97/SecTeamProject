package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Parent;

import java.util.List;

/**
 * Репозиторий для связи с таблицей усыновителей из БД.
 */
public interface ParentRepository extends JpaRepository<Parent, Long> {
    /**
     * Поиск усыновителя по chatID.
     * ChatID у каждого пользователя индивидуальный.
     *
     * @param chatId
     * @return Parent
     */
    Parent findByChatId(Long chatId);

    /**
     * Удаления усыновителя по chatID.
     * ChatID у каждого пользователя индивидуальный.
     *
     * @param chatId
     * @return
     */
    void deleteByChatId(Long chatId);
}
