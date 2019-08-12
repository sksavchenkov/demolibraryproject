package ru.bis.demolibraryproject.model.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.bis.demolibraryproject.model.Book;

@Getter
@Setter
@Component
public class BookDTO {


    private Long id;
    private String title;
    private String language;
    private AuthorDTO authorDTO;

    public BookDTO(Long id, String title, String language, AuthorDTO authorDTO) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.authorDTO = authorDTO;
    }
    public BookDTO() {
    }

    public static BookDTO toDTO(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getLanguage(), AuthorDTO.toDTO(book.getAuthor()));
    }
}
