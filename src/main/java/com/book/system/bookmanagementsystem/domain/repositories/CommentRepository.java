package com.book.system.bookmanagementsystem.domain.repositories;

import com.book.system.bookmanagementsystem.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    Page<Comment> findByBookId(Long bookId, Pageable pageable);
    Optional<Comment> findByIdAndBookId(Long id, Long bookId);
}
