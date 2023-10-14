package ru.secteam.teamwork.services.impl;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.repository.AnimalRepository;
import ru.secteam.teamwork.services.AnimalService;

@Service
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Animal add(Animal animal) {
        return animalRepository.save(animal);
    }

    @Override
    public Animal get(Long id) {
        return animalRepository.findById(id).orElse(null);
    }

    @Override
    public Animal update(Long id, Animal animal) {
        Animal savedAnimal = get(id);
        if (savedAnimal == null) {
            return null;
        }
        savedAnimal.setAgeMonth(animal.getAgeMonth());
        savedAnimal.setName(animal.getName());
        savedAnimal.setGender(animal.getGender());
        savedAnimal.setPetType(animal.getPetType());

        return animalRepository.save(savedAnimal);
    }

    @Override
    public String delete(Long id) {
        animalRepository.deleteById(id);
        return "Животное удалено";
    }
}
