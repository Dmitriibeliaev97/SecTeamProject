package ru.secteam.teamwork.service;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Volunteer;
import ru.secteam.teamwork.repository.VolunteerRepository;


@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    //найти волонтера в базе данных
    public Volunteer findVolunteer(Long id) {
        return volunteerRepository.findById(id).orElse(null);
    }

    //добавить волонтера в базу данных
    public Volunteer addVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    //удалить волонтера из базы
    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }

    //редактировать поля волонтера в базе
    public Volunteer editVolunteer(Long id, Volunteer volunteer) {
        Volunteer volunteerFromDB = findVolunteer(id);
        if (volunteerFromDB == null) {
            return null;
        }
        volunteerFromDB.setName(volunteer.getName());
        volunteerFromDB.setAge(volunteer.getAge());
        volunteerFromDB.setGender(volunteer.getGender());
        volunteerFromDB.setChatId(volunteer.getChatId());
        volunteerFromDB.setUserName(volunteer.getUserName());
        return volunteerRepository.save(volunteer);
    }

}
