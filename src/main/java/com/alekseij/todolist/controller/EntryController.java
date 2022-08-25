package com.alekseij.todolist.controller;

import com.alekseij.todolist.service.EntryService;
import com.alekseij.todolist.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "")
public class EntryController {

    private final EntryService entryService;

    @Autowired
    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping(path = "{entryId}")
    public ResponseEntity<Entry> getEntry(@PathVariable ("entryId") Long entryId) {
//        return entryService.getEntry(entryId);
        return ResponseEntity.ok().body(entryService.getEntry(entryId));
    }


    @GetMapping
    public List<Entry> getEntrys() {
        return entryService.getEntrys();
    }

    @PostMapping
    public void postNewEntry(@RequestBody Entry entry) {
        entryService.postNewEntry(entry);
    }

    @DeleteMapping(path = "{entryId}")
    public void deleteEntry(@PathVariable("entryId") Long entryId) {
        entryService.deleteEntry(entryId);
    }

    @PutMapping(path = "{entryId}")
    public void updateEntryData(
            @PathVariable ("entryId") Long entryId,
            @RequestParam(required = false) String data) {
            entryService.updateEntry(entryId, data);
    }

    @PutMapping(path = "status/{entryId}")
    public void updateIs_complete(
            @PathVariable ("entryId") Long entryId,
            @RequestParam(required = false) Boolean is_complete) {
            entryService.updateIs_complete(entryId, is_complete);
    }

}
