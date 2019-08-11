package ru.bis.demolibraryproject.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Author extends Identifiable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    public Author() {
    }

    public Author(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Book> bookList;

//    public Long getId() {
//        return id;
//    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }
}
