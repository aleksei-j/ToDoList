package com.alekseij.ToDoList.entry;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EntryService {

    private final EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<Entry> getEntrys() {
        return entryRepository.findAll();
    }

    public void postNewEntry(Entry entry) {
        System.out.println(entry);
        entryRepository.save(entry);
    }

    public void deleteEntry(Long entryId) {
        boolean exists = entryRepository.existsById(entryId);

        if (!exists) {
            throw new IllegalStateException("Entry with id: " + entryId + " does not exist.");
        }

        entryRepository.deleteById(entryId);
    }

    @Transactional
    public void updateEntry(Long entryId, String data) {

        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> new IllegalStateException("Entry with id: " + entryId + " does not exist."));
        entry.setData(data);
    }

    @Transactional
    public void updateIs_complete(Long entryId, Boolean is_complete) {

        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> new IllegalStateException("Entry with id: " + entryId + " does not exist."));
        entry.setIs_completed(is_complete);
    }
}

