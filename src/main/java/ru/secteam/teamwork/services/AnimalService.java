package ru.secteam.teamwork.services;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Animal;

/**
 * Сервис реализует CRUD операции с таблицей животных.
 * В дальнейшем при необходимости будут добавляться новые операции, помимо базовых CRUD
 */
public interface AnimalService {
    /**
     * С - create. Метод добавление нового животного в БД
     */
    Animal add(Animal animal);

    /**
     * R - read. Метод поиска животного в БД по его ID.
     */
    Animal get(Long id);

    /**
     * U - update. Метод для обновления данных по животному в БД.
     * Поиск животного, для которого требуется внести изменения, производится по ID.
     * Передается новая информацию, которая перезапишет старую, о животном и займет этот же ID.
     */
    Animal update(Long id, Animal animal);

    /**
     * D - delete. Метод удаления животного из БД.
     * Удаление производится по ID.
     * Метод будет возвращать информационное сообщение об удачном уделнии.
     */
    String delete(Long id);
}
