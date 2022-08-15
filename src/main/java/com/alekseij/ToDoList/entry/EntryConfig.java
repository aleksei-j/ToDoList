package com.alekseij.ToDoList.entry;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class EntryConfig {

    @Bean
    CommandLineRunner commandLineRunner(EntryRepository repository) {
        return args -> {
            Entry first = new Entry(
                    "Some text here"
            );
            Entry second = new Entry(
                    "Some more text here"
            );
            repository.saveAll(List.of(first, second));
        };
    }
}
