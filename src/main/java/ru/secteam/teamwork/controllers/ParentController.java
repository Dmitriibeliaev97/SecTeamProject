package ru.secteam.teamwork.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.services.ParentService;

import java.util.List;

/**
 * Контроллер сервиса усыновителей
 * @see ParentService
 */
@Slf4j
@RestController
@RequestMapping("/parents")
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    /**
     * Эндпоинт для добавления усыновителя.
     * @see ParentService#add(Parent)
     * @param parent
     * @return Добавленный усыновитель.
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
        log.info("Эндпоинт добавления усыновителя выполнен");
        return parentService.add(parent);
    }

    /**
     * Эндпоинт для поиска усыновителя.
     * @see ParentService#get(Long)
     * @param chatId
     * @return Информация об искомом усыновителе.
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
        log.info("Эндпоинт поиска усыновителя выполнен");
        return ResponseEntity.ok(parent);
    }

    /**
     * Эндпоинт для обновления данных усыновителя.
     * @see ParentService#update(Long, Parent)
     * @param chatId
     * @param parent
     * @return Обновленные данные об усыновителе.
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
            log.info("Эндпоинт обновления данных усыновителя выполнен");
            return ResponseEntity.ok(savedParent);
        }
    }

    /**
     * Эндпоинт для удаления усыновителя.
     * @see ParentService#delete(Long)
     * @param chatId
     * @return Информационное сообщение об удачном удалении усыновителя.
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
        log.info("Эндпоинт удаления усыновителя выполнен");
        return ResponseEntity.ok().build();
    }

    /**
     * Эндпоинт для вывода всех усыновителя.
     * @see ParentService#allParents()
     * @param
     * @return список всех усыновителей
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех усыновителей",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Parent.class)
                    )

            )
    })
    @GetMapping
    public List<Parent> allParents () {
        log.info("Эндпоинт вывода списка усыновителя выполнен");
        return parentService.allParents();
    }
}
