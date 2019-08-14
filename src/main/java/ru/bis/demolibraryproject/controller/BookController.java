package ru.bis.demolibraryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.model.Book;
import ru.bis.demolibraryproject.repository.AuthorRepository;
import ru.bis.demolibraryproject.repository.BookRepository;
import ru.bis.demolibraryproject.specification.AuthorSpecificationsBuilder;
import ru.bis.demolibraryproject.specification.BookSpecificationsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @PostMapping("/new/{title}/{language}/{authorid}")
    public ResponseEntity add(@PathVariable String title, @PathVariable String language, @PathVariable Long authorid) {
        if (title.isEmpty() || title.isEmpty() || authorid == null) return new ResponseEntity("Empty", HttpStatus.OK);
        Author author = authorRepository.findById(authorid).orElse(null);
        if (author == null) return new ResponseEntity("No author", HttpStatus.OK);
        Book book = new Book(title, language, author);
        bookRepository.save(book);
        return new ResponseEntity("Added", HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity("Empty", HttpStatus.OK);
        }
        Book book = null;
        book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity("Book not found", HttpStatus.OK);
        }
        return new ResponseEntity(book.getTitle(), HttpStatus.OK);
    }

    /**
     * Поиск автора книги по её названию.
     *
     * @param title Заголовок
     * @return
     */
    @GetMapping("/authorName/{title}")
    public ResponseEntity<Book> get(@PathVariable String title) {
        if (title == null) {
            return new ResponseEntity("Empty", HttpStatus.OK);
        }
        Book book = null;
        BookSpecificationsBuilder bookSB = new BookSpecificationsBuilder();
        Specification specification = bookSB.with("title", ":", title).build();
        List<Book> books = bookRepository.findAll(specification);
        List<Author> authors = books.stream()
                .map(b -> b.getAuthor())
                .distinct()
                .collect(Collectors.toList());
        String authorsNames = authors.stream().
                map(a -> a.getFullName() + " ").
                collect(Collectors.joining());

        if (books.isEmpty()) {
            return new ResponseEntity("Book not found", HttpStatus.OK);
        }
        return new ResponseEntity(authorsNames, HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity("Empty", HttpStatus.OK);
        }
        Book book = null;
        book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity("Book not found", HttpStatus.OK);
        }
        bookRepository.delete(book);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

    @PostMapping("/newTitle/{id}/{newTitle}")
    public ResponseEntity update(@PathVariable Long id, @PathVariable String newTitle) {
        String oldTitle;
        if (id == null || newTitle == null) {
            return new ResponseEntity("Empty", HttpStatus.OK);
        }
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
