package com.dh.sp.postgres.repo;

import com.dh.sp.postgres.model.Book;
import com.dh.sp.postgres.model.BookGenre;
import com.dh.sp.postgres.model.identity.BookId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, BookId> {

    Optional<Book> findByTitle(final String title);

    List<Book> findByAuthor(final String author, final Pageable pageable);

    @Query(value = "SELECT b FROM Book b WHERE b.bookGenre=:genre")
    List<Book> findByGenre_Query(@Param("genre") final BookGenre genre);
}
