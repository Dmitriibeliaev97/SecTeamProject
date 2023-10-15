package ru.secteam.teamwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.model.enums.PetType;
import ru.secteam.teamwork.services.ShelterService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShelterController.class)
class ShelterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShelterService shelterService;
    @InjectMocks
    private ShelterController shelterController;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void shouldAddShelter() throws Exception {
        long id = 1L;
        String name = "Приют кошек";
        String address = "Адресс";
        String info = "Информация";
        String instruction = "Инструкция";
        PetType petType = PetType.CAT;

        Shelter catShelter = new Shelter();
        catShelter.setId(id);
        catShelter.setName(name);
        catShelter.setAddress(address);
        catShelter.setInfo(info);
        catShelter.setInstruction(instruction);
        catShelter.setPetType(petType);

        when(shelterService.add(any(Shelter.class))).thenReturn(catShelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/shelters")
                        .content(objectMapper.writeValueAsBytes(catShelter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.address").value(address))
                .andExpect(jsonPath("$.info").value(info))
                .andExpect(jsonPath("$.instruction").value(instruction))
                .andExpect(jsonPath("$.petType").value(petType.toString()));
    }
    @Test
    public void shouldGetShelterById() throws Exception {
        long id = 1L;
        String name = "Приют кошек";
        String address = "Адресс";
        String info = "Информация";
        String instruction = "Инструкция";
        PetType petType = PetType.CAT;

        Shelter catShelter = new Shelter();
        catShelter.setId(id);
        catShelter.setName(name);
        catShelter.setAddress(address);
        catShelter.setInfo(info);
        catShelter.setInstruction(instruction);
        catShelter.setPetType(petType);

        when(shelterService.get(any(Long.class))).thenReturn(catShelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/shelters/" + id)
                        .content(objectMapper.writeValueAsBytes(catShelter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.address").value(address))
                .andExpect(jsonPath("$.info").value(info))
                .andExpect(jsonPath("$.instruction").value(instruction))
                .andExpect(jsonPath("$.petType").value(petType.toString()));

    }

    @Test
    public void shouldUpdateShelter() throws Exception {
        long id = 1L;
        String name = "Приют кошек";
        String address = "Адресс";
        String info = "Информация";
        String instruction = "Инструкция";
        PetType petType = PetType.CAT;

        Shelter catShelter = new Shelter();
        catShelter.setId(id);
        catShelter.setName(name);
        catShelter.setAddress(address);
        catShelter.setInfo(info);
        catShelter.setInstruction(instruction);
        catShelter.setPetType(petType);

        when(shelterService.add(any(Shelter.class))).thenReturn(catShelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/shelters/update{id}", id)
                        .content(objectMapper.writeValueAsBytes(catShelter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.address").value(address))
                .andExpect(jsonPath("$.info").value(info))
                .andExpect(jsonPath("$.instruction").value(instruction))
                .andExpect(jsonPath("$.petType").value(petType.toString()));
    }

    @Test
    public void shouldDeleteShelter() throws Exception {
        long id = 1L;
        String name = "Приют кошек";
        String address = "Адресс";
        String info = "Информация";
        String instruction = "Инструкция";
        PetType petType = PetType.CAT;

        Shelter catShelter = new Shelter();
        catShelter.setId(id);
        catShelter.setName(name);
        catShelter.setAddress(address);
        catShelter.setInfo(info);
        catShelter.setInstruction(instruction);
        catShelter.setPetType(petType);

        when(shelterService.add(any(Shelter.class))).thenReturn(catShelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/shelters" + id)
                        .content(objectMapper.writeValueAsBytes(catShelter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}