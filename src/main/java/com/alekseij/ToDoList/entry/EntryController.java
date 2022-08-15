package com.alekseij.ToDoList.entry;

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

    @GetMapping
    public List<Entry> getEntry() {
        return entryService.getEntrys();
    }

    @PostMapping
    public void postNewEntry(@RequestBody Entry entry  ) {
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

    @PutMapping(path = "one/{entryId}")
    public void updateIs_complete(
            @PathVariable ("entryId") Long entryId,
            @RequestParam(required = false) Boolean is_complete) {
            entryService.updateIs_complete(entryId, is_complete);
    }

}
