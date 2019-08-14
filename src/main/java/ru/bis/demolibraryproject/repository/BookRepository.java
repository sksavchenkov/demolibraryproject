package ru.bis.demolibraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.bis.demolibraryproject.model.Book;

public interface BookRepository extends  JpaRepository <Book, Long>, JpaSpecificationExecutor<Book> {
}
