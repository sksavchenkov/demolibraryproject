package ru.bis.demolibraryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.repository.AuthorRepository;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;

    @PostMapping("/add/{name}")
    public ResponseEntity add(@PathVariable String name) {
        if (name.isEmpty()) return new ResponseEntity<>("Empty", HttpStatus.OK);
        Author author = new Author(name);
        authorRepository.save(author);
        return new ResponseEntity<>("SUCCES", HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Author> get(@PathVariable Long id) {
        if (id == null) {return new ResponseEntity("Empty", HttpStatus.OK);}
        Author author = null;
        author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.OK);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
}
