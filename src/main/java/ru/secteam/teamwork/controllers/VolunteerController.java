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
import ru.secteam.teamwork.model.Volunteer;
import ru.secteam.teamwork.services.VolunteerService;

/**
 * Контроллер сервиса волонтеров
 * @see VolunteerService
 */
@Slf4j
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Добавление нового волонтера",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )

            )
    })
    @PostMapping
    public Volunteer add(@Parameter(description = "Все параметры добавляемого волонтера") @RequestBody Volunteer volunteer) {
        log.info("Эндпоинт добавления волонтера выполнен");
        return volunteerService.add(volunteer);
    }

    /**
     * @see VolunteerService#get(Long)
     * @param chatId
     * @return
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Получение информации о волонтере по ID",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )

            )
    })
    @GetMapping("/{chatId}")
    public ResponseEntity<Volunteer> get(@Parameter(description = "ID приюта") @PathVariable Long chatId) {
        Volunteer volunteer = volunteerService.get(chatId);
        if (volunteer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Эндпоинт поиска волонтера выполнен");
        return ResponseEntity.ok(volunteer);
    }

    /**
     * @see VolunteerService#update(Long, Volunteer)
     * @param chatId
     * @param volunteer
     * @return
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменение данных о приюте",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )

            )
    })
    @PutMapping("/update/{chatId}")
    public ResponseEntity<Volunteer> update(@Parameter(description = "ID изменяемого приюта") @PathVariable Long chatId,
                                            @Parameter(description = "Новые параметры приюта для замены") @RequestBody Volunteer volunteer) {
        Volunteer savedVolunteer = volunteerService.update(chatId, volunteer);
        if (savedVolunteer == null) {
            return ResponseEntity.badRequest().build();
        } else {
            log.info("Эндпоинт обновления данных волонтера выполнен");
            return ResponseEntity.ok(savedVolunteer);
        }
    }

    /**
     * @see VolunteerService#delete(Long)
     * @param chatId
     * @return
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаление приюта",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )

            )
    })
    @DeleteMapping("/{chatId}")
    public ResponseEntity delete(@Parameter(description = "ID удаляемого приюта") @PathVariable Long chatId) {
        volunteerService.delete(chatId);
        log.info("Эндпоинт удаления волонтера выполнен");
        return ResponseEntity.ok().build();
    }
}
