package ru.secteam.teamwork.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.services.ShelterService;

/**
 * Контроллер сервиса приютов
 * @see ShelterService
 */
@RestController
@RequestMapping("/shelters")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    /**
     * @see ShelterService#add(Shelter)
     * @param shelter
     * @return
     */
    @PostMapping
    public Shelter add(@RequestBody Shelter shelter) {
        return shelterService.add(shelter);
    }

    /**
     * @see ShelterService#get(Long)
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Shelter> get(@PathVariable Long id) {
        Shelter shelter = shelterService.get(id);
        if (shelter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(shelter);
    }

    /**
     * @see ShelterService#update(Long, Shelter)
     * @param id
     * @param shelter
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Shelter> update(@PathVariable Long id, @RequestBody Shelter shelter){
        Shelter savedShelter = shelterService.update(id, shelter);
        if (savedShelter == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(savedShelter);
        }
    }

    /**
     * @see ShelterService#delete(Long)
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        shelterService.delete(id);
        return ResponseEntity.ok().build();
    }
}
