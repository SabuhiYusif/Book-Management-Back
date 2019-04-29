package com.book.system.bookmanagementsystem.rest;

import com.book.system.bookmanagementsystem.domain.model.Comment;
import com.book.system.bookmanagementsystem.domain.repositories.BookRepository;
import com.book.system.bookmanagementsystem.domain.repositories.CommentRepository;
import com.book.system.bookmanagementsystem.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books/{bookId}/comments")
    public Page<Comment> getAllCommentsByBookId(@PathVariable(value = "bookId") Long bookId,
                                                Pageable pageable) {
        return commentRepository.findByBookId(bookId, pageable);
    }

    @PostMapping("/books/{bookId}/comments")
    public Comment createComment(@PathVariable (value = "bookId") Long bookId,
                                 @RequestBody Comment comment) {
        return bookRepository.findById(bookId).map(book -> {
            comment.setBook(book);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("Exception"));
    }

    @PutMapping("/books/{bookId}/comments/{commentId}")
    public Comment updateComment(@PathVariable (value = "bookId") Long bookId,
                                 @PathVariable (value = "commentId") Long commentId,
                                 @RequestBody Comment commentRequest) {
        if(!bookRepository.existsById(bookId)) {
            return null;
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("Exception"));
    }

    @DeleteMapping("/books/{bookId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "bookId") Long bookId,
                                           @PathVariable (value = "commentId") Long commentId) {
        return commentRepository.findByIdAndBookId(commentId, bookId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Exception"));
    }
}