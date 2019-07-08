package com.maybe.maybe.controller;

import com.maybe.maybe.dto.DeskDTO;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.service.DeskService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeskControllerTest {

    @Mock
    private DeskService deskService;

    @InjectMocks
    private DeskController deskController;

    private MockMvc mockMvc;

    private Desk desk;

    private DeskDTO deskDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(deskController).build();
        desk = new Desk();
        desk.setId(1L);
        desk.setName("name");
        deskDTO = new DeskDTO("name1");
    }

    @Test
    public void getDeskById() throws Exception {
        Long id = 1L;
        when(deskService.findById(id)).thenReturn(desk);

        mockMvc.perform(get("/api/desks/" + id))
                .andExpect(status().isOk());

    }

    @Test
    public void getDeskList() throws Exception {
        List<Desk> deskList = new ArrayList<>();
        deskList.add(desk);
        when(deskService.findAll()).thenReturn(deskList);

        mockMvc.perform(get("/api/desks"))
                .andExpect(status().isOk());
    }

    @Test
    public void createDesk() throws Exception {
        String name = "name";
        when(deskService.createFromDTO(deskDTO)).thenReturn(desk);

        mockMvc.perform(
                post("/api/desks/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateDeskById() throws Exception {
        Long id = 1L;
        String name = "name";
        when(deskService.createFromDTO(deskDTO)).thenReturn(desk);

        mockMvc.perform(put("/api/desks/" + id +"/" + name))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDeskById() throws Exception {
        Long id = 1L;
        when(deskService.deleteById(id)).thenReturn(desk);

        mockMvc.perform(delete("/api/desks/" + id))
                .andExpect(status().isOk());
    }
}