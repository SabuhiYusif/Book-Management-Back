package com.book.system.bookmanagementsystem.rest;

import com.book.system.bookmanagementsystem.domain.model.Book;
import com.book.system.bookmanagementsystem.domain.repositories.BookRepository;
import com.book.system.bookmanagementsystem.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Exception"));
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/books/{bookId}")
    public Book updateBook(@PathVariable Long bookId, @RequestBody Book bookRequest) {
        return bookRepository.findById(bookId).map(book -> {
            book.setTitle(bookRequest.getTitle());
            book.setAuthor(bookRequest.getAuthor());
            book.setISBN(bookRequest.getISBN());
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("Exception"));
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        return bookRepository.findById(bookId).map(book -> {
            bookRepository.delete(book);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Exception"));
    }

}
