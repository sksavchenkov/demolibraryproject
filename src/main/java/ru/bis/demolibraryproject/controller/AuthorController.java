package ru.bis.demolibraryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.model.Book;
import ru.bis.demolibraryproject.model.DTO.AuthorDTO;
import ru.bis.demolibraryproject.repository.AuthorRepository;
import ru.bis.demolibraryproject.specification.AuthorSpecificationsBuilder;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    //todo: make it private and final (You can use constructor injection)
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorDTO authorDTO;

    /** todo:
     * Не совсем понятно, что ты тут хотел сделать. Если добавить нового автора, то сделай просто POST /authors
     * и в body передавай объект Author {"name":"Vova Putin"}
     */
    @PostMapping("/name/{name}")
    public ResponseEntity add(@PathVariable String name) {
        if (name.isEmpty()) return new ResponseEntity<>("Empty", HttpStatus.OK); //Надо бы валидировать обязательные поля и кидать ошибку (400 - bad request или 422 - Unprocessable Entity)
        Author author = new Author(name);
        authorRepository.save(author);
        return new ResponseEntity<>("SUCCES", HttpStatus.OK);
    }

    @GetMapping("/author/{id}") // Тут не нужен /author. Почитай про RESTful
    public ResponseEntity get(@PathVariable Long id) {
        if (id == null) {return new ResponseEntity("Empty", HttpStatus.OK);} //Проверь, исполнится ли этот код вообще. Есть сомнения
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.OK); // Лучше отвечать ошибкой 404
        }
        if (!author.getBookList().isEmpty()) {
            for (Book book : author.getBookList()) {
                System.out.println(book.getTitle()); //todo: а зачем это тут? Отладочную информацию лучше писать в логи (@Slf4j -> log.debug(...))
            }
        }
//        AuthorDTO tmp = authorDTO.toDTO(author); //todo: не оставляй комментарии в мастере
        return new ResponseEntity(String.valueOf(author.getBookList().size()), HttpStatus.OK);
        //todo: Тоже не очень очевидно, что должен ты получаешь в этом методе.
        // Несоответствие endpoint'а и результата. По идее, нужно возвращать автора.
    }

    @GetMapping("/name/{fullname}")
    public ResponseEntity get(@PathVariable String fullname) {
        if (fullname == null) {return new ResponseEntity("Empty", HttpStatus.OK);} //todo: мы опять сюда, скорее всего не попадем
        Author author = null;
        AuthorSpecificationsBuilder authorSB= new AuthorSpecificationsBuilder();
        Specification specification = authorSB.with("fullName", ":", fullname).build();
        List<Author> authors = authorRepository.findAll(specification);
        if (authors.size() == 0) { //todo: тут лучше проверять на пустоту (.isEmpty())
            return new ResponseEntity("Author not found", HttpStatus.OK); //todo: 404 ошибка
        }

//        AuthorDTO tmp = authorDTO.toDTO(author);
        return new ResponseEntity(String.valueOf(authors), HttpStatus.OK); //todo: опять не очень ясно, зачем тут String.valueOf()?
    }

    @DeleteMapping("/del/{id}") //todo: опять же, посмотри на RESTful, у тебя запросы получаются вида DELETE http://url/authors/del...И так понятно, что это удаление
    public ResponseEntity delete(@PathVariable Long id) {
        if (id == null) {return new ResponseEntity("Empty", HttpStatus.OK);} //todo: тоже самое
        Author author = null;
        author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.OK);
        }
        authorRepository.delete(author);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

    @PostMapping("/update/{id}/{fullname}") //todo: лучше использовать PUT для изменений. update в path - лишний
    public ResponseEntity update(@PathVariable Long id, @PathVariable String fullname) {
        String oldFullName;
        if (id == null) {return new ResponseEntity("Empty", HttpStatus.OK);} //todo: везде проверь эти места, тоже самое
        Author author = null;
        author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.OK);//todo: 404
        }
        oldFullName = author.getFullName();
        author.setFullName(fullname);
        authorRepository.save(author);
        return new ResponseEntity(oldFullName + " -> " + author.getFullName(), HttpStatus.OK);
    }

}
