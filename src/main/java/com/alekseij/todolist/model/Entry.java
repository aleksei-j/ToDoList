package com.alekseij.todolist.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Entry {

    @Id
    @SequenceGenerator(
            name = "entry_sequence",
            sequenceName = "entry_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "entry_sequence"
    )
    private Long id;
    private String data;
    private LocalDate date = LocalDate.now();
    private boolean isCompleted = false;

    public Entry() {
    }

    public Entry(String data) {
        this.data = data;
    }

    public Entry(Long id, String data) {
        this.id = id;
        this.data = data;
    }

    public Entry(Long id, String data, LocalDate date, boolean isCompleted) {
        this.id = id;
        this.data = data;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
}
