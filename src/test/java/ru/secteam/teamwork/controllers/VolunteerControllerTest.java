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
import ru.secteam.teamwork.model.Volunteer;
import ru.secteam.teamwork.model.enums.Gender;
import ru.secteam.teamwork.services.VolunteerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VolunteerController.class)
class VolunteerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VolunteerService volunteerService;
    @InjectMocks
    private VolunteerController volunteerController;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldAddVolunteer() throws Exception {
        long chatId = 1L;
        int age = 26;
        String name = "Дмитрий";
        String userName = "dmitrii_beliaev";
        Gender gender = Gender.MALE;

        Volunteer volunteer = new Volunteer();
        volunteer.setChatId(chatId);
        volunteer.setName(name);
        volunteer.setAge(age);
        volunteer.setUserName(userName);
        volunteer.setGender(gender);

        when(volunteerService.add(any(Volunteer.class))).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteers")
                        .content(objectMapper.writeValueAsBytes(volunteer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.userName").value(userName))
                .andExpect(jsonPath("$.gender").value(gender.toString()));
    }
    @Test
    public void shouldGetVolunteerByChatId() throws Exception {
        long chatId = 1L;
        int age = 26;
        String name = "Дмитрий";
        String userName = "dmitrii_beliaev";
        Gender gender = Gender.MALE;

        Volunteer volunteer = new Volunteer();
        volunteer.setChatId(chatId);
        volunteer.setName(name);
        volunteer.setAge(age);
        volunteer.setUserName(userName);
        volunteer.setGender(gender);

        when(volunteerService.get(any(Long.class))).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteers/" + chatId)
                        .content(objectMapper.writeValueAsBytes(volunteer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.userName").value(userName))
                .andExpect(jsonPath("$.gender").value(gender.toString()));
    }

    @Test
    public void shouldUpdateVolunteer() throws Exception {
        long chatId = 1L;
        int age = 26;
        String name = "Дмитрий";
        String userName = "dmitrii_beliaev";
        Gender gender = Gender.MALE;

        Volunteer volunteer = new Volunteer();
        volunteer.setChatId(chatId);
        volunteer.setName(name);
        volunteer.setAge(age);
        volunteer.setUserName(userName);
        volunteer.setGender(gender);

        when(volunteerService.update(chatId, volunteer)).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/volunteers/update/{chatId}", chatId)
                        .content(objectMapper.writeValueAsBytes(volunteer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.userName").value(userName))
                .andExpect(jsonPath("$.gender").value(gender.toString()));
    }
    @Test
    public void shouldDeleteVolunteer() throws Exception {
        long chatId = 1L;
        int age = 26;
        String name = "Дмитрий";
        String userName = "dmitrii_beliaev";
        Gender gender = Gender.MALE;

        Volunteer volunteer = new Volunteer();
        volunteer.setChatId(chatId);
        volunteer.setName(name);
        volunteer.setAge(age);
        volunteer.setUserName(userName);
        volunteer.setGender(gender);

        when(volunteerService.add(any(Volunteer.class))).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteers/" + chatId)
                        .content(objectMapper.writeValueAsBytes(volunteer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}