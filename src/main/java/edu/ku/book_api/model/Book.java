package edu.ku.book_api.model;

public record Book(
        Long id,
        String title,
        String author,
        int availableCopies
) {
}