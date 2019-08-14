package ru.bis.demolibraryproject.model.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.model.Book;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class AuthorDTO {
    private Long id;
    private String fullname;
    private List<BookDTO> bookList = new ArrayList<>(); //todo: лучше просто назвать books

    public AuthorDTO(Long id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    public AuthorDTO() {
    }

    public static AuthorDTO toDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO(author.getId(), author.getFullName());
         if (!author.getBookList().isEmpty()) {
             for (Book book: author.getBookList()) {
                 authorDTO.getBookList().add(BookDTO.toDTO(book));
             }
         }
             return authorDTO; //todo: форматирование уехало
    }
}
