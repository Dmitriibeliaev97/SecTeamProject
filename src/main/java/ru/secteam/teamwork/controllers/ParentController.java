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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Добавление нового усыновителя",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Parent.class)
                    )

            )
    })
    @PostMapping
    public Parent add(@Parameter(description = "Все параметры добавляемого усыновителя") @RequestBody Parent parent) {
        return parentService.add(parent);
    }

    /**
     * @see ParentService#get(Long)
     * @param chatId
     * @return
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Получение информации об усыновителе по ID",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Parent.class)
                    )

            )
    })
    @GetMapping("/{chatId}")
    public ResponseEntity<Parent> get(@Parameter(description = "ID усыновителя") @PathVariable Long chatId) {
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменение данных об усыновителе",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Parent.class)
                    )

            )
    })
    @PutMapping("/update/{chatId}")
    public ResponseEntity<Parent> update(@Parameter(description = "ID изменяемого усыновителя")@PathVariable Long chatId,
                                         @Parameter(description = "Новые параметры усыновителя для замены") @RequestBody Parent parent) {
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаление усыновителя",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Parent.class)
                    )

            )
    })
    @DeleteMapping("/{chatId}")
    public ResponseEntity delete(@Parameter(description = "ID удаляемого усыновителя") @PathVariable Long chatId) {
        parentService.delete(chatId);
        return ResponseEntity.ok().build();
    }
}
