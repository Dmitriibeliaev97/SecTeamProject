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
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.services.ShelterService;

/**
 * Контроллер сервиса приютов
 * @see ShelterService
 */
@Slf4j
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Добавление нового приюта",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Shelter.class)
                    )

            )
    })
    @PostMapping
    public Shelter add(@Parameter(description = "Все параметры добавляемого приюта") @RequestBody Shelter shelter) {
        log.info("Эндпоинт добавления приюта выполнен");
        return shelterService.add(shelter);
    }

    /**
     * @see ShelterService#get(Long)
     * @param id
     * @return
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Получение информации о приюте по ID",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Shelter.class)
                    )

            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Shelter> get(@Parameter(description = "ID приюта") @PathVariable Long id) {
        Shelter shelter = shelterService.get(id);
        if (shelter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Эндпоинт поиска приюта выполнен");
        return ResponseEntity.ok(shelter);
    }

    /**
     * @see ShelterService#update(Long, Shelter)
     * @param id
     * @param shelter
     * @return
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменение данных о приюте",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Shelter.class)
                    )

            )
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Shelter> update(@Parameter(description = "ID изменяемого приюта") @PathVariable Long id,
                                          @Parameter(description = "Новые параметры приюта для замены") @RequestBody Shelter shelter){
        Shelter savedShelter = shelterService.update(id, shelter);
        if (savedShelter == null) {
            return ResponseEntity.badRequest().build();
        } else {
            log.info("Эндпоинт обновления данных приюта выполнен");
            return ResponseEntity.ok(savedShelter);
        }
    }

    /**
     * @see ShelterService#delete(Long)
     * @param id
     * @return
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаление приюта",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Shelter.class)
                    )

            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@Parameter(description = "ID удаляемого приюта") @PathVariable Long id) {
        shelterService.delete(id);
        log.info("Эндпоинт удаления приюта выполнен");
        return ResponseEntity.ok().build();
    }
}
