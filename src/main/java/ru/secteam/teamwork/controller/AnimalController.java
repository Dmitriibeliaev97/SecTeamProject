package ru.secteam.teamwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.service.AnimalService;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private  final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    //добавление нового животного
    @PostMapping
    public ResponseEntity<Animal> createAnimal(
            @RequestBody Animal animal) {
        Animal createdAnimal = animalService.addAnimal(animal);
        return ResponseEntity.ok(createdAnimal);
    }

    //поиск животного по идентификационному номеру
    @GetMapping("/{id}")
    public ResponseEntity<Animal> readAnimal(
            @PathVariable Long id
    ) {
        Animal animal = animalService.findAnimal(id);
        if (animal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animal);
    }

    //внести изменения в поля животного
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(
            @PathVariable Long id,
            @RequestBody Animal animal
    ) {
        Animal updatedAnimal = animalService.editAnimal(id, animal);
        if (updatedAnimal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAnimal);
    }

    //удаление животного из списка
    @DeleteMapping("/{id}")
    public void deleteAnimal(
            @PathVariable Long id
    ) {
        animalService.deleteAnimal(id);
    }

}
