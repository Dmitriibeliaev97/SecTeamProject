package ru.secteam.teamwork.services;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.model.Volunteer;

import java.util.List;

/**
 * Сервис реализует CRUD операции с таблицей волонтеров.
 * В дальнейшем при необходимости будут добавляться новые операции, помимо базовых CRUD
 */
public interface VolunteerService {
    /**
     * С - create. Метод добавление нового волонтера в БД
     */
    Volunteer add(Volunteer volunteer);

    /**
     * R - read. Метод поиска волонтера в БД по его chat ID.
     */
    Volunteer get(Long chatId);

    /**
     * U - update. Метод для обновления данных по волонтеру в БД.
     * Поиск животного, для которого требуется внести изменения, производится по chat ID.
     * Передается новая информацию, которая перезапишет старую, о волонтере и займет этот же chat ID.
     */
    Volunteer update(Long chatId, Volunteer volunteer);

    /**
     * D - delete. Метод удаления волонтера из БД.
     * Удаление производится по chat ID.
     * Метод будет возвращать информационное сообщение об удачном уделнии.
     */
    String delete(Long chatId);

    /**
     * Метод для получения списка всех волонтеров.
     */
    List<Volunteer> allVolunteers();

}
