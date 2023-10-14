package ru.secteam.teamwork.services.impl;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.model.Volunteer;
import ru.secteam.teamwork.repository.VolunteerRepository;
import ru.secteam.teamwork.services.VolunteerService;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public Volunteer add(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer get(Long chatId) {
        return volunteerRepository.findByChatId(chatId);
    }

    @Override
    public Volunteer update(Long chatId, Volunteer volunteer) {
        Volunteer savedVolunteer = get(chatId);
        if (savedVolunteer == null) {
            return null;
        }
        savedVolunteer.setAge(volunteer.getAge());
        savedVolunteer.setName(volunteer.getName());
        savedVolunteer.setGender(volunteer.getGender());
        return volunteerRepository.save(savedVolunteer);
    }

    @Override
    public String delete(Long chatId) {
        volunteerRepository.deleteByChatId(chatId);
        return "Волонтер удалён";
    }
}
