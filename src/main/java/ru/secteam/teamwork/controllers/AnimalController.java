package ru.secteam.teamwork.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.services.AnimalService;

import javax.persistence.ManyToOne;

/**
 * Контроллер сервиса животных
 *
 * @see AnimalService
 */
@RestController
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    /**
     * @param animal
     * @return
     * @see AnimalService#add(Animal)
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Добавление нового животного",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Animal.class)
                    )

            )
    })
    @PostMapping
    public Animal add(@Parameter(description = "Все параметры добавляемого животного") @RequestBody Animal animal) {
        return animalService.add(animal);
    }

    /**
     * @param id
     * @return
     * @see AnimalService#get(Long)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Animal> get(@PathVariable Long id) {
        Animal animal = animalService.get(id);
        if (animal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(animal);
    }

    /**
     * @param id
     * @param animal
     * @return
     * @see AnimalService#update(Long, Animal)
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Animal> update(@PathVariable Long id, @RequestBody Animal animal) {
        Animal savedAnimal = animalService.update(id, animal);
        if (savedAnimal == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(savedAnimal);
        }
    }

    /**
     * @param id
     * @return
     * @see AnimalService#delete(Long)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        animalService.delete(id);
        return ResponseEntity.ok().build();
    }

}
