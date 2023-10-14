package ru.secteam.teamwork.services.impl;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.repository.ParentRepository;
import ru.secteam.teamwork.services.ParentService;

@Service
public class ParentServiceImpl implements ParentService {
    private final ParentRepository parentRepository;

    public ParentServiceImpl(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public Parent add(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public Parent get(Long chatId) {
        return parentRepository.findByChatId(chatId);
    }

    @Override
    public Parent update(Long chatId, Parent parent) {
        Parent savedParent = get(chatId);
        if (savedParent == null) {
            return null;
        }
        savedParent.setAge(parent.getAge());
        savedParent.setName(parent.getName());
        savedParent.setGender(parent.getGender());
        return parentRepository.save(savedParent);
    }

    @Override
    public String delete(Long chatId) {
        parentRepository.deleteByChatId(chatId);
        return "Усыновитель удалён";
    }
}
