package ru.secteam.teamwork.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.secteam.teamwork.model.Volunteer;
import ru.secteam.teamwork.services.VolunteerService;

/**
 * Контроллер сервиса волонтеров
 * @see VolunteerService
 */
@RestController
@RequestMapping("/volunteers")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    /**
     * @see VolunteerService#add(Volunteer)
     * @param volunteer
     * @return
     */
    @PostMapping
    public Volunteer add(@RequestBody Volunteer volunteer) {
        return volunteerService.add(volunteer);
    }

    /**
     * @see VolunteerService#get(Long)
     * @param chatId
     * @return
     */
    @GetMapping("/{chatId}")
    public ResponseEntity<Volunteer> get(@PathVariable Long chatId) {
        Volunteer volunteer = volunteerService.get(chatId);
        if (volunteer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(volunteer);
    }

    /**
     * @see VolunteerService#update(Long, Volunteer)
     * @param chatId
     * @param volunteer
     * @return
     */
    @PutMapping("/update/{chatId}")
    public ResponseEntity<Volunteer> update(@PathVariable Long chatId, @RequestBody Volunteer volunteer) {
        Volunteer savedVolunteer = volunteerService.update(chatId, volunteer);
        if (savedVolunteer == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(savedVolunteer);
        }
    }

    /**
     * @see VolunteerService#delete(Long)
     * @param chatId
     * @return
     */
    @DeleteMapping("/{chatId}")
    public ResponseEntity delete(@PathVariable Long chatId) {
        volunteerService.delete(chatId);
        return ResponseEntity.ok().build();
    }
}
