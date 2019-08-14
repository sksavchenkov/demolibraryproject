package ru.bis.demolibraryproject.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Data
public class Author extends Identifiable {
    @Column(name = "fullName", nullable = false)
    private String fullName;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Book> bookList; //todo тут тоже лучше просто books

    public Author() {
    }

    public Author(String fullName) {
        this.fullName = fullName;
    }

//    public String getFullName() {
//        return fullName;
//    }
//
//
//
////    public Long getId() {
////        return id;
////    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
////    public void setId(Long id) {
////        this.id = id;
////    }
    //todo: а этот метод не нужен, когда есть lombok
    public List<Book> getBookList(){
        return this.bookList;
    }
}
