package edu.ku.book_api.model;

public record BookInput(
        String title,
        String author,
        int availableCopies
) {
}