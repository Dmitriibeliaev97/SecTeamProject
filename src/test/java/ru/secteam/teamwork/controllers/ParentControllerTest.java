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
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.model.enums.Gender;
import ru.secteam.teamwork.services.ParentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParentController.class)
class ParentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ParentService parentService;
    @InjectMocks
    private ParentController parentController;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldAddParent() throws Exception {
        String parentName = "Дмитрий";
        int parentAge = 26;
        Gender parentGender = Gender.MALE;
        String dateOfAdoption = "14.10.2023";

        Parent dmitriiParent = new Parent();
        dmitriiParent.setName(parentName);
        dmitriiParent.setAge(parentAge);
        dmitriiParent.setGender(parentGender);
        dmitriiParent.setDateOfAdoption(dateOfAdoption);

        when(parentService.add(any(Parent.class))).thenReturn(dmitriiParent);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parents")
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(parentName))
                .andExpect(jsonPath("$.age").value(parentAge))
                .andExpect(jsonPath("$.gender").value(parentGender))
                .andExpect(jsonPath("$.dateOfAdoption").value(dateOfAdoption));
    }

    @Test
    public void shouldGetParentByChatId() throws Exception {
        String parentName = "Дмитрий";
        int parentAge = 26;
        Gender parentGender = Gender.MALE;
        String dateOfAdoption = "14.10.2023";
        Long chatId = 11L;

        Parent dmitriiParent = new Parent();
        dmitriiParent.setChatId(chatId);
        dmitriiParent.setName(parentName);
        dmitriiParent.setAge(parentAge);
        dmitriiParent.setGender(parentGender);
        dmitriiParent.setDateOfAdoption(dateOfAdoption);

        when(parentService.get(any(Long.class))).thenReturn(dmitriiParent);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/parents/" + chatId)
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(parentName))
                .andExpect(jsonPath("$.age").value(parentAge))
                .andExpect(jsonPath("$.gender").value(parentGender))
                .andExpect(jsonPath("$.dateOfAdoption").value(dateOfAdoption));
    }

    @Test
    public void shouldUpdateParent() throws Exception {
        String parentName = "Дмитрий";
        int parentAge = 26;
        Gender parentGender = Gender.MALE;
        String dateOfAdoption = "14.10.2023";
        Long chatId = 11L;

        Parent dmitriiParent = new Parent();
        dmitriiParent.setChatId(chatId);
        dmitriiParent.setName(parentName);
        dmitriiParent.setAge(parentAge);
        dmitriiParent.setGender(parentGender);
        dmitriiParent.setDateOfAdoption(dateOfAdoption);

        when(parentService.update(chatId, dmitriiParent)).thenReturn(dmitriiParent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/parents/update{id}", chatId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(parentName))
                .andExpect(jsonPath("$.age").value(parentAge))
                .andExpect(jsonPath("$.gender").value(parentGender))
                .andExpect(jsonPath("$.dateOfAdoption").value(dateOfAdoption));
    }

    @Test
    public void shouldDeleteParent() throws Exception {
        String parentName = "Дмитрий";
        int parentAge = 26;
        Gender parentGender = Gender.MALE;
        String dateOfAdoption = "14.10.2023";
        Long chatId = 11L;

        Parent dmitriiParent = new Parent();
        dmitriiParent.setChatId(chatId);
        dmitriiParent.setName(parentName);
        dmitriiParent.setAge(parentAge);
        dmitriiParent.setGender(parentGender);
        dmitriiParent.setDateOfAdoption(dateOfAdoption);

        when(parentService.add(any(Parent.class))).thenReturn(dmitriiParent);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/parents/" + chatId)
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}