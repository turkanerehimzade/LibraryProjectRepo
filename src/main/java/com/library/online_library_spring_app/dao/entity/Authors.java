package com.library.online_library_spring_app.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "authors", schema = "online_library_schema")
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Column(name="authors_is_active")
    private Boolean authorsIsActive;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "authors",
            cascade = CascadeType.ALL, orphanRemoval = true
    )
    @JsonIgnoreProperties("authors")//?
    private Set<Books> books = new HashSet<>();


    public void addBook(Books book) {
        if (this.books == null) {
            this.books = new HashSet<>();
        }
        this.books.add(book);
        book.setAuthors(this);
    }


    public void removeBook(Books book) {
        books.remove(book);
        book.setAuthors(null);
    }



}
