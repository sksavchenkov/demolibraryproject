package ru.bis.demolibraryproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@Setter
public class Book extends Identifiable{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "Id", nullable = false)
//    private Long id;
    @Column(name = "title", length = 64, nullable = false)
    private String title;
    @Column(name = "language", length = 64, nullable = false)
    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public Book(String title, String language, Author author) {
        this.title = title;
        this.language = language;
        this.author = author;
    }

    public Book() {
    }
}
