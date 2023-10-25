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
 *
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
     *
     * @param parent
     * @return Добавленный усыновитель.
     * @see ParentService#add(Parent)
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
     *
     * @param chatId
     * @return Информация об искомом усыновителе.
     * @see ParentService#get(Long)
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
     *
     * @param chatId
     * @param parent
     * @return Обновленные данные об усыновителе.
     * @see ParentService#update(Long, Parent)
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
    public ResponseEntity<Parent> update(@Parameter(description = "ID изменяемого усыновителя") @PathVariable Long chatId,
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
     *
     * @param chatId
     * @return Информационное сообщение об удачном удалении усыновителя.
     * @see ParentService#delete(Long)
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
     *
     * @param
     * @return список всех усыновителей
     * @see ParentService#allParents()
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
    public List<Parent> allParents() {
        log.info("Эндпоинт вывода списка усыновителя выполнен");
        return parentService.allParents();
    }

    /**
     * Эндпоинт для добавления животного усыновителю.
     *
     * @param chatId
     * @param id
     * @return Добавленный усыновитель.
     * @see ParentService#add(Parent)
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Добавление животного усыновителю",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Parent.class)
                    )

            )
    })
    @PutMapping("/add-animal/{chatId}/{id}")
    public Parent addAnimal(@Parameter(description = "Chat ID усыновителя") @PathVariable Long chatId,
                            @Parameter(description = "ID животного") @PathVariable Long id) {
        log.info("Эндпоинт добавления животного усыновителю выполнен");
        return parentService.addAnimal(chatId, id);
    }

    /**
     * Эндпоинт для добавления даты последнего отчета усыновителя.
     *
     * @param chatId
     * @param date
     * @return Добавленный усыновитель.
     * @see ParentService#add(Parent)
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Добавление даты последнего отчета усыновителю",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Parent.class)
                    )

            )
    })
    @PutMapping("/add-date-of-report/{chatId}/{date}")
    public Parent addDateOfReport(@Parameter(description = "Chat ID усыновителя") @PathVariable Long chatId,
                                  @Parameter(description = "Дата последнего отчета. Формат: гггг-мм-дд") @PathVariable String date) {
        log.info("Эндпоинт добавления даты последнего отчета усыновителю выполнен");
        return parentService.addDateOfReport(chatId, date);
    }
}
