package com.alekseij.todolist.controller;

import com.alekseij.todolist.service.EntryService;
import com.alekseij.todolist.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Entry getEntry(@PathVariable ("entryId") Long entryId) {
        return entryService.getEntry(entryId);

    }


    @GetMapping
    public List<Entry> getEntries() {
        return entryService.getEntries();
    }

    @PostMapping
    public Long postNewEntry(@RequestBody Entry entry) {
        entryService.postNewEntry(entry);
        return entry.getId();
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
    public void updateIsComplete(
            @PathVariable ("entryId") Long entryId,
            @RequestParam(required = false) Boolean is_complete) {
            entryService.updateIsComplete(entryId, is_complete);
    }

}
