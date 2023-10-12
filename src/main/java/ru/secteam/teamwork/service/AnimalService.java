package ru.secteam.teamwork.service;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.repository.AnimalRepository;


@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService( AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    //найти животного в базе данных
    public Animal findAnimal(Long id) {
        return animalRepository.findById(id).orElse(null);
    }

    //добавление животного
    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    // удаление животного из таблицы
    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    // редактирование полей животного
    public Animal editAnimal(Long id, Animal animal) {
        Animal animalFromDB = findAnimal(id);
        if (animalFromDB == null) {
            return null;
        }

        animalFromDB.setAgeMonth(animal.getAgeMonth());
        animalFromDB.setGender(animal.getGender());
        animalFromDB.setName(animal.getName());
        return animalRepository.save(animalFromDB);
    }
}
