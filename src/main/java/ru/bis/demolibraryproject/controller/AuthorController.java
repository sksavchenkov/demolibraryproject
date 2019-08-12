package ru.bis.demolibraryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.model.Book;
import ru.bis.demolibraryproject.model.DTO.AuthorDTO;
import ru.bis.demolibraryproject.repository.AuthorRepository;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorDTO authorDTO;

    @PostMapping("/add/{name}")
    public ResponseEntity add(@PathVariable String name) {
        if (name.isEmpty()) return new ResponseEntity<>("Empty", HttpStatus.OK);
        Author author = new Author(name);
        authorRepository.save(author);
        return new ResponseEntity<>("SUCCES", HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        if (id == null) {return new ResponseEntity("Empty", HttpStatus.OK);}
        Author author = null;
        author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.OK);
        }
        if (!author.getBookList().isEmpty()) {
            for (Book book : author.getBookList()) {
                System.out.println(book.getTitle());
            }
        }
//        AuthorDTO tmp = authorDTO.toDTO(author);
        return new ResponseEntity(String.valueOf(author.getBookList().size()), HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (id == null) {return new ResponseEntity("Empty", HttpStatus.OK);}
        Author author = null;
        author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.OK);
        }
        authorRepository.delete(author);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

    @PostMapping("/update/{id}/{fullname}")
    public ResponseEntity update(@PathVariable Long id, @PathVariable String fullname) {
        String oldFullName;
        if (id == null) {return new ResponseEntity("Empty", HttpStatus.OK);}
        Author author = null;
        author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.OK);
        }
        oldFullName = author.getFullName();
        author.setFullName(fullname);
        authorRepository.save(author);
        return new ResponseEntity(oldFullName + " -> " + author.getFullName(), HttpStatus.OK);
    }

}
