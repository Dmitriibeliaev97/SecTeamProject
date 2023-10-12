package ru.secteam.teamwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.service.ParentService;

@RestController
@RequestMapping("/parent")
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    //добавление нового родителя для животных
    @PostMapping
    public ResponseEntity<Parent> createParent(
            @RequestBody Parent parent
    ) {
        Parent createdParent = parentService.addParent(parent);
        return ResponseEntity.ok(parent);
    }

    //поиск родителя по id
    @GetMapping("/{id}")
    public ResponseEntity<Parent> readParent(
            @PathVariable Long id
    ) {
        Parent parent = parentService.findParent(id);
        if (parent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parent);
    }

    //изменить поля родителя
    @PutMapping("/{id}")
    public ResponseEntity<Parent> updateParent(
            @PathVariable Long id,
            @RequestBody Parent parent
    ) {
        Parent updatedParent = parentService.editParent(id, parent);
        if (updatedParent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedParent);
    }

    @DeleteMapping("/{id}")
    public void deleteParent(
            @PathVariable Long id
    ) {
        parentService.deleteParent(id);
    }



}
