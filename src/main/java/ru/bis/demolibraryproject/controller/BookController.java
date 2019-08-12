package ru.bis.demolibraryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.model.Book;
import ru.bis.demolibraryproject.repository.AuthorRepository;
import ru.bis.demolibraryproject.repository.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @PostMapping("/add/{title}/{language}/{authorid}")
    public ResponseEntity add(@PathVariable String title, @PathVariable String language, @PathVariable Long authorid) {
        if (title.isEmpty() || title.isEmpty() || authorid == null) return new ResponseEntity("Empty", HttpStatus.OK);
        Author author = authorRepository.findById(authorid).orElse(null);
        if (author == null) return new ResponseEntity("No author", HttpStatus.OK);
        Book book = new Book(title,language, author);
        bookRepository.save(book);
        return new ResponseEntity("Added", HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        if (id == null) {return new ResponseEntity("Empty", HttpStatus.OK);}
        Book book = null;
        book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity("Book not found", HttpStatus.OK);
        }
        return new ResponseEntity(book.getTitle(), HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (id == null) {return new ResponseEntity("Empty", HttpStatus.OK);}
        Book book = null;
        book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity("Book not found", HttpStatus.OK);
        }
        bookRepository.delete(book);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

    @PostMapping("/update/{id}/{newTitle}")
    public ResponseEntity update(@PathVariable Long id, @PathVariable String newTitle) {
        String oldTitle;
        if (id == null || newTitle == null) {return new ResponseEntity("Empty", HttpStatus.OK);}
        Book book = null;
        book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity("Book not found", HttpStatus.OK);
        }
        oldTitle = book.getTitle();
        book.setTitle(newTitle);
        bookRepository.save(book);
        return new ResponseEntity(oldTitle + " -> " + book.getTitle(), HttpStatus.OK);
    }
}
