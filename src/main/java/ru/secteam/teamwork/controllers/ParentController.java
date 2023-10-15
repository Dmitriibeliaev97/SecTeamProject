package ru.secteam.teamwork.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.services.ParentService;

/**
 * Контроллер сервиса усыновителей
 * @see ParentService
 */
@RestController
@RequestMapping("/parents")
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    /**
     * @see ParentService#add(Parent)
     * @param parent
     * @return
     */
    @PostMapping
    public Parent add(@RequestBody Parent parent) {
        return parentService.add(parent);
    }

    /**
     * @see ParentService#get(Long)
     * @param chatId
     * @return
     */
    @GetMapping("/{chatId}")
    public ResponseEntity<Parent> get(@PathVariable Long chatId) {
        Parent parent = parentService.get(chatId);
        if (parent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(parent);
    }

    /**
     * @see ParentService#update(Long, Parent)
     * @param chatId
     * @param parent
     * @return
     */
    @PutMapping("/update/{chatId}")
    public ResponseEntity<Parent> update(@PathVariable Long chatId, @RequestBody Parent parent) {
        Parent savedParent = parentService.update(chatId, parent);
        if (savedParent == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(savedParent);
        }
    }

    /**
     * @see ParentService#delete(Long)
     * @param chatId
     * @return
     */
    @DeleteMapping("/{chatId}")
    public ResponseEntity delete(@PathVariable Long chatId) {
        parentService.delete(chatId);
        return ResponseEntity.ok().build();
    }
}
