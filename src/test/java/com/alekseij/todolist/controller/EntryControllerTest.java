package com.alekseij.todolist.controller;

import com.alekseij.todolist.model.Entry;
import com.alekseij.todolist.service.EntryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest
class EntryControllerTest {

    @MockBean
    private EntryService entryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEntry() throws Exception {

        Entry entry = new Entry(1L,"some text");

        when(entryService.getEntry(eq(1L))).thenReturn(entry);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/{entryId}", 1L ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is("some text")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed", Matchers.is(false)));
    }

    @Test
    void getEntries() throws Exception {
        when(entryService.getEntries()).thenReturn(List.of(
                new Entry(1L,"some text"),
                new Entry(2L,"some other text"))
        );

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].data", Matchers.is("some text")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].completed", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].data", Matchers.is("some other text")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].completed", Matchers.is(false)));
    }

    @Test
    void postNewEntry() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": \"some text\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());


        verify(entryService).postNewEntry(any(Entry.class));
    }

    @Test
    void deleteEntry() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/{entryId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateEntryData() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/{entryId}", 1L, "some text")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateIs_complete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/{entryId}", 1L, true)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}