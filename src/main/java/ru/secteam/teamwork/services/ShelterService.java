package ru.secteam.teamwork.services;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Shelter;

/**
 * Сервис реализует CRUD операции с таблицей приютов.
 * В дальнейшем при необходимости будут добавляться новые операции, помимо базовых CRUD
 */
public interface ShelterService {
    /**
     * С - create. Метод добавление нового приюта в БД
     */
    Shelter add(Shelter shelter);

    /**
     * R - read. Метод поиска приюта в БД по его ID.
     */
    Shelter get(Long id);

    /**
     * U - update. Метод для обновления данных по приюту в БД.
     * Поиск приюта, для которого требуется внести изменения, производится по ID.
     * Передается новая информацию, которая перезапишет старую, о приюте и займет этот же ID.
     */
    Shelter update(Long id, Shelter shelter);

    /**
     * D - delete. Метод удаления приюта из БД.
     * Удаление производится по ID.
     * Метод будет возвращать информационное сообщение об удачном уделнии.
     */
    String delete(Long id);
}
