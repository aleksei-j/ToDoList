package com.alekseij.todolist.service;

import com.alekseij.todolist.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {
}
