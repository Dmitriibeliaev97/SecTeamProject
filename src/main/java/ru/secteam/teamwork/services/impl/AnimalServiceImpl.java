package ru.secteam.teamwork.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.repository.AnimalRepository;
import ru.secteam.teamwork.services.AnimalService;

/**
 * Имплементация сервиса животных
 * @see AnimalService
 */
@Service
@Slf4j
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    /**
     * Добавление нового животного.
     * Используется метод репозитория {@link AnimalRepository#save(Object)}
     * @param animal
     * @return Добавлено животное.
     * @see AnimalService#add(Animal)
     */
    @Override
    public Animal add(Animal animal) {
        return animalRepository.save(animal);
    }

    /**
     * Получение информации по животному через ID
     * Используетяс метод репозитория {@link AnimalRepository#findById(Object)}
     * @param id
     * @return Вся нформация по искомому животному.
     * @see AnimalService#get(Long)
     */
    @Override
    public Animal get(Long id) {
        return animalRepository.findById(id).orElse(null);

    }

    /**
     * Метод обновления ифнормации о животном.
     * Создается новый объект животного, ему присваивается переданный ID.
     * Этому объекту сохраняются переданная информация по животному.
     * @param id
     * @param animal
     * @return Данные по животному обновлены
     * @see AnimalService#update(Long, Animal)
     */
    @Override
    public Animal update(Long id, Animal animal) {
        // создается новый объект животного.
        // передаётся ему ID существующего животного, которого необходимо отредактировать
        Animal savedAnimal = get(id);
        if (savedAnimal == null) {
            return null;
        }
        // передаются новые параметры для животного
        savedAnimal.setAgeMonth(animal.getAgeMonth());
        savedAnimal.setName(animal.getName());
        savedAnimal.setGender(animal.getGender());
        savedAnimal.setPetType(animal.getPetType());

        return animalRepository.save(savedAnimal);
    }

    /**
     * Метод удаления животного.
     * Используется метод репозитория {@link AnimalRepository#deleteById(Object)}
     * @param id
     * @return Информационное сообщение об успешном удалении животного.
     * @see AnimalService#delete(Long)
     */
    @Override
    public String delete(Long id) {
        animalRepository.deleteById(id);
        return "Животное удалено";
    }
}
