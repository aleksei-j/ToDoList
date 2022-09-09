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
                .andExpect(MockMvcResultMatchers.jsonPath("$._completed", Matchers.is(false)));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]._completed", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].data", Matchers.is("some other text")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]._completed", Matchers.is(false)));
    }


    /*
    I don't understand this test. When I run the test and print out MockHttpServletRequest it just has the
    following: {"data": "some text"} is there a way how to test with an actual object? How do we now that the Id
    is created and the date is inserted automatically? And also check that is_completed is set to false?
     */
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

    /*
    Same here, we did not create any object that we can delete, how do we know that method actually deletes
    anything?
     */

    @Test
    void deleteEntry() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/{entryId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /*
    Same here, how do we know it can update anything?
     */
    @Test
    void updateEntryData() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/{entryId}", 1L, "some text")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /*
    And the same here.
     */
    @Test
    void updateIs_complete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/{entryId}", 1L, true)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}