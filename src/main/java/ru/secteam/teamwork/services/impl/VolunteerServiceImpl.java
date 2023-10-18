package ru.secteam.teamwork.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.model.Volunteer;
import ru.secteam.teamwork.repository.ParentRepository;
import ru.secteam.teamwork.repository.ShelterRepository;
import ru.secteam.teamwork.repository.VolunteerRepository;
import ru.secteam.teamwork.services.ParentService;
import ru.secteam.teamwork.services.ShelterService;
import ru.secteam.teamwork.services.VolunteerService;

import java.util.List;

/**
 * Имплементация сервиса волонтеров
 * @see VolunteerService
 */
@Service
@Slf4j
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Добавление нового волонтера.
     * Используется метод репозитория {@link VolunteerRepository#save(Object)}
     * @param volunteer
     * @return Добавлен волонтер.
     * @see VolunteerService#add(Volunteer)
     */
    @Override
    public Volunteer add(Volunteer volunteer) {
        log.info("Метод добавления волонтера выполнен");
        return volunteerRepository.save(volunteer);
    }

    /**
     * Получение информации по волонтеру через chat ID
     * Используетяс метод репозитория {@link VolunteerRepository#findById(Object)}
     * @param chatId
     * @return Вся нформация по искомому волонтеру.
     * @see VolunteerService#get(Long)
     */
    @Override
    public Volunteer get(Long chatId) {
        log.info("Метод поиска волонтера выполнен");
        return volunteerRepository.findByChatId(chatId);
    }

    /**
     * Метод обновления ифнормации о волонтере.
     * Создается новый объект усыновителя, ему присваивается переданный chat ID.
     * Этому объекту сохраняются переданная информация по волонтеру.
     * @param chatId
     * @param volunteer
     * @return Данные по волонтеру обновлены
     * @see VolunteerService#update(Long, Volunteer)
     */
    @Override
    public Volunteer update(Long chatId, Volunteer volunteer) {
        // создается новый объект волонтера.
        // передаётся ему chat ID существующего волонтера, которого необходимо отредактировать
        Volunteer savedVolunteer = get(chatId);
        if (savedVolunteer == null) {
            return null;
        }
        // передаются новые параметры для волонтера
        savedVolunteer.setAge(volunteer.getAge());
        savedVolunteer.setName(volunteer.getName());
        savedVolunteer.setGender(volunteer.getGender());
        log.info("Метод обновления данных волонтера выполнен");
        return volunteerRepository.save(savedVolunteer);
    }

    /**
     * Метод удаления волонтера.
     * Используется метод репозитория {@link VolunteerRepository#deleteById(Object)}
     * @param chatId
     * @return Информационное сообщение об успешном удалении волонтера.
     * @see VolunteerService#delete(Long)
     */
    @Override
    public String delete(Long chatId) {
        volunteerRepository.deleteByChatId(chatId);
        log.info("Метод удаления волонтера выполнен");
        return "Волонтер удалён";
    }

    /**
     * Метод выведения списка всех волонтеров.
     * Используется метод репозитория {@link VolunteerRepository#findAll()}
     * @return Список всех волонтеров.
     * @see VolunteerService#allVolunteers()
     */
    @Override
    public List<Volunteer> allVolunteers() {
        log.info("Метод выведения списках всех волонтеров выполнен");
        return volunteerRepository.findAll();
    }
}
