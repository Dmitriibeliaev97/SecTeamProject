package ru.secteam.teamwork.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.repository.AnimalRepository;
import ru.secteam.teamwork.repository.ParentRepository;
import ru.secteam.teamwork.repository.ShelterRepository;
import ru.secteam.teamwork.services.AnimalService;
import ru.secteam.teamwork.services.ParentService;
import ru.secteam.teamwork.services.ShelterService;

import java.util.List;

/**
 * Имплементация сервиса приюта
 * @see ShelterService
 */
@Service
@Slf4j
public class ShelterServiceImpl implements ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    /**
     * Добавление нового приюта.
     * Используется метод репозитория {@link ShelterRepository#save(Object)}
     * @param shelter
     * @return Добавлен приют.
     * @see ShelterService#add(Shelter)
     */
    @Override
    public Shelter add(Shelter shelter) {
        log.info("Метод добавления приюта выполнен");
        return shelterRepository.save(shelter);
    }

    /**
     * Получение информации по приюту через ID
     * Используетяс метод репозитория {@link ShelterRepository#findById(Object)}
     * @param id
     * @return Вся нформация по искомому приюту.
     * @see ShelterService#get(Long)
     */
    @Override
    public Shelter get(Long id) {
        log.info("Метод поиска приюта выполнен");
        return shelterRepository.findById(id).orElse(null);
    }

    /**
     * Метод обновления ифнормации о приюте.
     * Создается новый объект приюта, ему присваивается переданный ID.
     * Этому объекту сохраняются переданная информация по приюте.
     * @param id
     * @param shelter
     * @return Данные по приюту обновлены
     * @see ShelterService#update(Long, Shelter)
     */
    @Override
    public Shelter update(Long id, Shelter shelter) {
        // создается новый объект приюта.
        // передаётся ему ID существующего приюта, которого необходимо отредактировать
        Shelter savedShelter = get(id);
        if (savedShelter == null) {
            return null;
        }
        // передаются новые параметры для приюта
        savedShelter.setName(shelter.getName());
        savedShelter.setAddress(shelter.getAddress());
        savedShelter.setInfo(shelter.getInfo());
        savedShelter.setInstruction(shelter.getInstruction());
        savedShelter.setPetType(shelter.getPetType());
        log.info("Метод обновления данных приюта выполнен");
        return shelterRepository.save(savedShelter);
    }
    /**
     * Метод удаления приюта.
     * Используется метод репозитория {@link ShelterRepository#deleteById(Object)}
     * @param id
     * @return Информационное сообщение об успешном удалении приюта.
     * @see ShelterService#delete(Long)
     */
    @Override
    public String delete(Long id) {
        shelterRepository.deleteById(id);
        log.info("Метод удаления приюта выполнен");
        return "Приют удален";
    }

    /**
     * Метод выведения списка всех приютов.
     * Используется метод репозитория {@link ShelterRepository#findAll()}
     * @return Список всех приютов.
     * @see ShelterService#allShelters()
     */
    @Override
    public List<Shelter> allShelters() {
        log.info("Метод выведения списках всех приютов выполнен");
        return shelterRepository.findAll();
    }
}
