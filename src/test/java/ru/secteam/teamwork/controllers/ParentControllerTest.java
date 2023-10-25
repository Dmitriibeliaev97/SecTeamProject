package ru.secteam.teamwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.model.enums.ButtonSelection;
import ru.secteam.teamwork.model.enums.Gender;
import ru.secteam.teamwork.model.enums.PetType;
import ru.secteam.teamwork.services.ParentService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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
                .andExpect(jsonPath("$.gender").value(parentGender.toString()))
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
                .andExpect(jsonPath("$.gender").value(parentGender.toString()))
                .andExpect(jsonPath("$.dateOfAdoption").value(dateOfAdoption));
    }

    @Test
    public void shouldUpdateParent() throws Exception {
        long chatId = 11L;
        int parentAge = 26;
        String parentName = "Дмитрий";
        Gender parentGender = Gender.MALE;
        String dateOfAdoption = "14.10.2023";

        Parent dmitriiParent = new Parent();
        dmitriiParent.setChatId(chatId);
        dmitriiParent.setName(parentName);
        dmitriiParent.setAge(parentAge);
        dmitriiParent.setGender(parentGender);
        dmitriiParent.setDateOfAdoption(dateOfAdoption);

        when(parentService.update(chatId, dmitriiParent)).thenReturn(dmitriiParent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/parents/update/{chatId}", chatId)
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(parentName))
                .andExpect(jsonPath("$.age").value(parentAge))
                .andExpect(jsonPath("$.gender").value(parentGender.toString()))
                .andExpect(jsonPath("$.dateOfAdoption").value(dateOfAdoption))
        ;
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

    @Test
    public void shouldGetAllParents() throws Exception {
        List<Parent> parents = new ArrayList<>();
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

        parents.add(dmitriiParent);

        when(parentService.allParents()).thenReturn(parents);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/parents")
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(parents.size())));
    }

    @Test
    public void shouldAddAnimalToParent() throws Exception {
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

        dmitriiParent.setAnimal(tuzikAnimal);

        when(parentService.addAnimal(chatId, id)).thenReturn(dmitriiParent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/parents/add-animal/{chatId}/{id}", chatId, id)
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(parentName))
                .andExpect(jsonPath("$.age").value(parentAge))
                .andExpect(jsonPath("$.gender").value(parentGender.toString()))
                .andExpect(jsonPath("$.dateOfAdoption").value(dateOfAdoption));
    }

    @Test
    public void shouldAddDateOfReport() throws Exception {
        String parentName = "Дмитрий";
        int parentAge = 26;
        Gender parentGender = Gender.MALE;
        String dateOfAdoption = "14.10.2023";
        Long chatId = 11L;
        String dateOfReport = "2023-10-25";

        Parent dmitriiParent = new Parent();
        dmitriiParent.setChatId(chatId);
        dmitriiParent.setName(parentName);
        dmitriiParent.setAge(parentAge);
        dmitriiParent.setGender(parentGender);
        dmitriiParent.setDateOfAdoption(dateOfAdoption);
        dmitriiParent.setReport(dateOfReport);

        when(parentService.addDateOfReport(chatId, dateOfReport)).thenReturn(dmitriiParent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/parents/add-date-of-report/{chatId}/{date}", chatId, dateOfReport)
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(parentName))
                .andExpect(jsonPath("$.age").value(parentAge))
                .andExpect(jsonPath("$.gender").value(parentGender.toString()))
                .andExpect(jsonPath("$.dateOfAdoption").value(dateOfAdoption))
                .andExpect(jsonPath("$.report").value(dateOfReport));
    }
    
    @Test
    public void shouldSendMessageToParent() throws Exception {
        String textMessage = "Текст";
        
        String parentName = "Дмитрий";
        int parentAge = 26;
        Gender parentGender = Gender.MALE;
        String parentUsername = "dmitrii_beliaev";
        String dateOfAdoption = "14.10.2023";

        Parent dmitriiParent = new Parent();
        dmitriiParent.setName(parentName);
        dmitriiParent.setAge(parentAge);
        dmitriiParent.setGender(parentGender);
        dmitriiParent.setDateOfAdoption(dateOfAdoption);
        dmitriiParent.setUserName(parentUsername);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parents/send-message/{parentUsername}/{textMessage}", parentUsername, textMessage)
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void ShouldSendFinishMessage() throws Exception {
        String parentName = "Дмитрий";
        int parentAge = 26;
        Gender parentGender = Gender.MALE;
        String parentUsername = "dmitrii_beliaev";
        String dateOfAdoption = "14.10.2023";

        Parent dmitriiParent = new Parent();
        dmitriiParent.setName(parentName);
        dmitriiParent.setAge(parentAge);
        dmitriiParent.setGender(parentGender);
        dmitriiParent.setDateOfAdoption(dateOfAdoption);
        dmitriiParent.setUserName(parentUsername);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parents/send-finish-message/{parentUsername}", parentUsername)
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void ShouldSendFailedMessage() throws Exception {
        String parentName = "Дмитрий";
        int parentAge = 26;
        Gender parentGender = Gender.MALE;
        String parentUsername = "dmitrii_beliaev";
        String dateOfAdoption = "14.10.2023";

        Parent dmitriiParent = new Parent();
        dmitriiParent.setName(parentName);
        dmitriiParent.setAge(parentAge);
        dmitriiParent.setGender(parentGender);
        dmitriiParent.setDateOfAdoption(dateOfAdoption);
        dmitriiParent.setUserName(parentUsername);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parents/send-failed-message/{parentUsername}", parentUsername)
                        .content(objectMapper.writeValueAsBytes(dmitriiParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}