package com.book.system.bookmanagementsystem.domain.repositories;

import com.book.system.bookmanagementsystem.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
