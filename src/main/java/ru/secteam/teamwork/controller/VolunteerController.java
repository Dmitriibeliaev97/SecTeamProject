package ru.secteam.teamwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.secteam.teamwork.model.Volunteer;
import ru.secteam.teamwork.service.VolunteerService;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    //добавление нового волонтера
    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(
            @RequestBody Volunteer volunteer
    ) {
        Volunteer createdVolunteer = volunteerService.addVolunteer(volunteer);
        return ResponseEntity.ok(createdVolunteer);
    }

    //поиск волонтера по id
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> readVolunteer(
            @PathVariable Long id
    ) {
        Volunteer volunteer = volunteerService.findVolunteer(id);
        if (volunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteer);
    }

    //удалить волонтера из списка
    @DeleteMapping("/{id}")
    public void deleteVolunteer(
            @PathVariable Long id
    ) {
        volunteerService.deleteVolunteer(id);
    }

    //внести изменения в поля волонтера
    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(
            @PathVariable Long id,
            @RequestBody Volunteer volunteer
    ) {
        Volunteer updatedVolunteer = volunteerService.editVolunteer(id, volunteer);
        if (updatedVolunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedVolunteer);
    }
}
