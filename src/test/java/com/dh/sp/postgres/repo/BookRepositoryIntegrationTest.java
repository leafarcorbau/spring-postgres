package com.dh.sp.postgres.repo;

import com.dh.sp.postgres.IntegrationTest;
import com.dh.sp.postgres.model.Book;
import com.dh.sp.postgres.model.BookGenre;
import com.dh.sp.postgres.model.TestBook;
import com.dh.sp.postgres.model.identity.BookId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void init(){
        bookRepository.deleteAll();
    }
    @Test
    void shouldSaveBook() {
        //Given
        final UUID seed = UUID.randomUUID();
        final Book book = TestBook.getInstance(seed).build();
        final Book expected = TestBook.getInstance(seed).build();

        //When
        bookRepository.save(book);

        //Then
        Optional<Book> result = bookRepository.findById(new BookId(seed));
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    void shouldFindBooksByTitle() {
        //Given
        final UUID seed = UUID.randomUUID();
        final Book book = TestBook.getInstance(seed).build();
        final Book expected = TestBook.getInstance(seed).build();
        bookRepository.save(book);

        //When
        Optional<Book> result = bookRepository.findByTitle(book.getTitle());

        //Then

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    void shouldFindBooksByAuthor() {
        //Given
        final String author = "rafael";
        final Book book1 = TestBook.getInstance(UUID.randomUUID()).title("a").author(author).build();
        final Book book2 = TestBook.getInstance(UUID.randomUUID()).title("b").author(author).build();
        final Book book3 = TestBook.getInstance(UUID.randomUUID()).title("c").author(author).build();
        final Book book4 = TestBook.getInstance(UUID.randomUUID()).build();
        final Book book5 = TestBook.getInstance(UUID.randomUUID()).build();
        final List<Book> expected = List.of(book1);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);

       final Pageable sortedByTitleDesc =
                PageRequest.of(1, 2, Sort.by("title").descending());

        //When
        final List<Book> result = bookRepository.findByAuthor(author, sortedByTitleDesc);

        //Then
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldFindBooksByGenreUsingQuery() {
        //Given
        final BookGenre bookGenre = BookGenre.ADVENTURE;
        final Book book1 = TestBook.getInstance(UUID.randomUUID()).bookGenre(bookGenre).build();
        final Book book2 = TestBook.getInstance(UUID.randomUUID()).build();
        final Book book3 = TestBook.getInstance(UUID.randomUUID()).build();
        final List<Book> expected = List.of(book1);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        //When
        final List<Book> result = bookRepository.findByGenre_Query(bookGenre);

        //Then
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testStream() {
        //Given
        final BookGenre bookGenre = BookGenre.ADVENTURE;
        final Book book1 = TestBook.getInstance(UUID.randomUUID()).bookGenre(bookGenre).build();
        final Book book2 = TestBook.getInstance(UUID.randomUUID()).build();
        final Book book3 = TestBook.getInstance(UUID.randomUUID()).build();
        final List<Book> expected = List.of(book1);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        //When
        final List<Book> result = bookRepository.findByGenre_Query(bookGenre);
        result.stream().filter(book -> book.getTitle().equals(expected)).findAny().isPresent();
    }
}
