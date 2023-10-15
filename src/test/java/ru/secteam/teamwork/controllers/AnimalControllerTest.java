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
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.enums.Gender;
import ru.secteam.teamwork.model.enums.PetType;
import ru.secteam.teamwork.services.AnimalService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnimalController.class)
class AnimalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimalService animalService;
    @InjectMocks
    private AnimalController animalController;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldAddAnimal() throws Exception {
        long id = 1L;
        int ageMonth = 6;
        String name = "Тузик";
        Gender gender = Gender.MALE;
        PetType petType = PetType.DOG;

        Animal tuzikAnimal = new Animal();
        tuzikAnimal.setId(id);
        tuzikAnimal.setAgeMonth(ageMonth);
        tuzikAnimal.setName(name);
        tuzikAnimal.setGender(gender);
        tuzikAnimal.setPetType(petType);

        when(animalService.add(any(Animal.class))).thenReturn(tuzikAnimal);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/animals")
                        .content(objectMapper.writeValueAsBytes(tuzikAnimal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.ageMonth").value(ageMonth))
                .andExpect(jsonPath("$.gender").value(gender.toString()))
                .andExpect(jsonPath("$.petType").value(petType.toString()));
    }

    @Test
    public void shouldGetAnimalById() throws Exception {
        long id = 1L;
        int ageMonth = 6;
        String name = "Тузик";
        Gender gender = Gender.MALE;
        PetType petType = PetType.DOG;

        Animal tuzikAnimal = new Animal();
        tuzikAnimal.setId(id);
        tuzikAnimal.setAgeMonth(ageMonth);
        tuzikAnimal.setName(name);
        tuzikAnimal.setGender(gender);
        tuzikAnimal.setPetType(petType);

        when(animalService.get(any(Long.class))).thenReturn(tuzikAnimal);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/animals/" + id)
                        .content(objectMapper.writeValueAsBytes(tuzikAnimal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.ageMonth").value(ageMonth))
                .andExpect(jsonPath("$.gender").value(gender.toString()))
                .andExpect(jsonPath("$.petType").value(petType.toString()));
    }

    @Test
    public void shouldUpdateAnimal() throws Exception {
        long id = 1L;
        int ageMonth = 6;
        String name = "Тузик";
        Gender gender = Gender.MALE;
        PetType petType = PetType.DOG;

        Animal tuzikAnimal = new Animal();
        tuzikAnimal.setId(id);
        tuzikAnimal.setAgeMonth(ageMonth);
        tuzikAnimal.setName(name);
        tuzikAnimal.setGender(gender);
        tuzikAnimal.setPetType(petType);

        when(animalService.update(id, tuzikAnimal)).thenReturn(tuzikAnimal);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/animals/update/{id}", id)
                        .content(objectMapper.writeValueAsBytes(tuzikAnimal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.ageMonth").value(ageMonth))
                .andExpect(jsonPath("$.gender").value(gender.toString()))
                .andExpect(jsonPath("$.petType").value(petType.toString()));
    }

    @Test
    public void shouldDeleteAnimal() throws Exception {
        long id = 1L;
        int ageMonth = 6;
        String name = "Тузик";
        Gender gender = Gender.MALE;
        PetType petType = PetType.DOG;

        Animal tuzikAnimal = new Animal();
        tuzikAnimal.setId(id);
        tuzikAnimal.setAgeMonth(ageMonth);
        tuzikAnimal.setName(name);
        tuzikAnimal.setGender(gender);
        tuzikAnimal.setPetType(petType);

        when(animalService.add(any(Animal.class))).thenReturn(tuzikAnimal);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/animals/" + id)
                        .content(objectMapper.writeValueAsBytes(tuzikAnimal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}