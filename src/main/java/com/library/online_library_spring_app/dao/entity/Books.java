package com.library.online_library_spring_app.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "books", schema = "online_library_schema")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "book_name")
    private String bookName;
    private String category;
    private String language;
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    @Column(name = "page_count")
    private int pageCount;
    @Column(name= "count")
    private int count;
    @Column(name = "book_is_active")
    private Boolean bookIsActive ;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnoreProperties("books")
    private Authors authors;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
    public void addAuthor(Authors authors) {
        if (this.authors == null) {
            this.authors = new Authors();
        }
        this.setAuthors(authors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return pageCount == books.pageCount && bookIsActive == books.bookIsActive && Objects.equals(id, books.id) && Objects.equals(bookName, books.bookName) && Objects.equals(category, books.category) && Objects.equals(language, books.language) && Objects.equals(publicationDate, books.publicationDate) && Objects.equals(createdAt, books.createdAt) && Objects.equals(updatedAt, books.updatedAt) && Objects.equals(authors, books.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookName, category, language, publicationDate, pageCount, bookIsActive, createdAt, updatedAt, authors);
    }

}
