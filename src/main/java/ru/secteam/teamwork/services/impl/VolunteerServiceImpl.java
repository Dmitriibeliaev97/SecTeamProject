package ru.secteam.teamwork.services.impl;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.model.Volunteer;
import ru.secteam.teamwork.repository.ParentRepository;
import ru.secteam.teamwork.repository.VolunteerRepository;
import ru.secteam.teamwork.services.ParentService;
import ru.secteam.teamwork.services.VolunteerService;
/**
 * Имплементация сервиса волонтеров
 * @see VolunteerService
 */
@Service
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
        return "Волонтер удалён";
    }
}
