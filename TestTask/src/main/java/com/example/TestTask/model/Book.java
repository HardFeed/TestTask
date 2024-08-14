package com.example.TestTask.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[A-Z].*$", message = "Title must start with a capital letter")
    @Size(min = 3, message = "Title must be at least 3 characters long")
    private String title;
    @Pattern(regexp = "^[A-Z][a-zA-Z]*\\s[A-Z][a-zA-Z]*$", message = "Author must contain two capitalized words with a space between")
    private String author;
    @Min(value = 0, message = "Amount should be at least 0")
    private int amount;
}
