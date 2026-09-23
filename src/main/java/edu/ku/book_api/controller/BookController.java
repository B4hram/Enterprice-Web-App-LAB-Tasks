package edu.ku.book_api.controller;

import edu.ku.book_api.model.Book;
import edu.ku.book_api.model.BookInput;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final List<Book> books = new ArrayList<>(List.of(
            new Book(1L, "Java Programming", "John Smith", 5),
            new Book(2L, "Web Development", "Sara Ahmad", 3),
            new Book(3L, "Database Systems", "Ali Khan", 4)
    ));

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookInput input
    ) {

        for (int i = 0; i < books.size(); i++) {

            Book existingBook = books.get(i);

            if (existingBook.id().equals(bookId)) {

                Book updatedBook = new Book(
                        existingBook.id(),
                        input.title(),
                        input.author(),
                        input.availableCopies()
                );

                books.set(i, updatedBook);

                return ResponseEntity.ok(updatedBook);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable Long bookId
    ) {

        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).id().equals(bookId)) {

                books.remove(i);

                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }
}