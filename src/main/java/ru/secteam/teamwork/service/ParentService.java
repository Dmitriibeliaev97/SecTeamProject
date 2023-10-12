package ru.secteam.teamwork.service;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.repository.ParentRepository;

@Service
public class ParentService {
    private final ParentRepository parentRepository;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    //найти родителя в базе данных
    public Parent findParent(Long id) {
        return parentRepository.findById(id).orElse(null);
    }

    //добавить родителя
    public Parent addParent(Parent parent) {
        return parentRepository.save(parent);
    }

    //удалить родителя
    public void deleteParent(Long id) {
        parentRepository.deleteById(id);
    }

    //редактировать поля родителя
    public Parent editParent(Long id, Parent parent) {
        Parent parentFromDB = findParent(id);
        if (parentFromDB == null) {
            return null;
        }
        parentFromDB.setAnimal(parent.getAnimal());
        parentFromDB.setName(parent.getName());
        parentFromDB.setGender(parent.getGender());
        parentFromDB.setAge(parent.getAge());
        parentFromDB.setChatId(parent.getChatId());
        parentFromDB.setUserName(parent.getUserName());
        return parentRepository.save(parent);
    }


}
