package com.dh.sp.postgres.model;

import com.dh.sp.postgres.model.identity.BookId;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id"))
    private BookId id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private BookGenre bookGenre;

    @Column(name = "copies_count")
    private Integer copiesCount;
}
