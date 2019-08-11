package ru.bis.demolibraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bis.demolibraryproject.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}