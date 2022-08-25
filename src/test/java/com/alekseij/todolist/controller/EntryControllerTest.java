package com.alekseij.todolist.controller;

import com.alekseij.todolist.model.Entry;
import com.alekseij.todolist.service.EntryService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest
class EntryControllerTest {

    @MockBean
    private EntryService entryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEntry() throws Exception {

        Entry entry = new Entry(1L,"some text");

//        when(entryService.getEntrys()).thenReturn(List.of(
//                new Entry(1L,"some text")
//        );

        when(entryService.getEntry(eq(1L))).thenReturn(
                entry);

        this.mockMvc
                .perform(get("/", 1L))
//                .andDo(MockMvcResultHandlers.print())
//                .perform(MockMvcRequestBuilders.get("/",1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is("some text")))
                .andExpect(MockMvcResultMatchers.jsonPath("date", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("is_completed", Matchers.is(false)));
    }

    @Test
    void getEntrys() throws Exception {
        when(entryService.getEntrys()).thenReturn(List.of(
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].is_completed", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].data", Matchers.is("some other text")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].is_completed", Matchers.is(false)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void postNewEntry() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": \"some text\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());


        verify(entryService).postNewEntry(any(Entry.class));
    }

    @Test
    void deleteEntry() throws Exception {

//        when(entryService.getEntrys()).thenReturn(List.of(
//                new Entry(1L,"some text"),
//                new Entry(2L,"some other text"))
//        );

        Entry entry = new Entry(1L,"some text");




//        when(entryService.deleteEntry(eq(1L))).

        mockMvc.perform(MockMvcRequestBuilders.delete("/", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateEntryData() throws Exception {

        Entry entry = new Entry(1L,"some text");

        when(entryService.getEntry(eq(1L))).thenReturn(entry);

        System.out.println(entry.getData());

        mockMvc.perform(put("/",1L, "dslkfj"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].data", Matchers.is("dslkfj")));

    }

    @Test
    void updateIs_complete() {
    }

}