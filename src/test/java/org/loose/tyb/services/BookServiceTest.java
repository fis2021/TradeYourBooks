package org.loose.tyb.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.tyb.exceptions.BookExists;
import org.loose.tyb.model.Book;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class BookServiceTest {
    public static final String a = "gigi";
    public static final String b = "fram ursul polar";
    public static final String c = "preda";
    public static final int an = 1;
    public static final String publicator = "Artemis";
    public static final int exemplare = 2;

    @AfterEach
    void tearDown() {
        BookService.getDatabase().close();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        BookService.initDatabase();
    }

    @Test
    void testDatabaseIsInitializedAndNoBookIsPersisted() {
        assertThat(BookService.allBooks()).isNotNull();
        assertThat(BookService.allBooks()).isEmpty();
    }

    @Test
    void testBookIsAddedToDatabase() throws BookExists {
        BookService.addBook(a, b, c, an, publicator, exemplare);
        assertThat(BookService.allBooks()).isNotEmpty();
        assertThat(BookService.ListaLib()).isNotEmpty();
        assertThat(BookService.Lista()).isEmpty();
        assertThat(BookService.allBooks()).size().isEqualTo(1);
        Book book = BookService.allBooks().get(0);
        assertThat(book).isNotNull();
        assertThat(book.getOwner()).isEqualTo(a);
        assertThat(book.getBookname()).isEqualTo(b);
        assertThat(book.getAuthor()).isEqualTo(c);
        assertThat(book.getYear()).isEqualTo(an);
        assertThat(book.getPublisher()).isEqualTo(publicator);
        assertThat(book.getNoEx()).isEqualTo(exemplare);
    }

    @Test
    void testCanNotAddTheSameBook() {
        assertThrows(BookExists.class, () -> {
            BookService.addBook(a, b, c, an, publicator, exemplare);
            BookService.addBook(a, b, c, an, publicator, exemplare);
        });
    }

    @Test
    void testEditBook() throws BookExists {
        BookService.addBook(a, b, c, an, publicator, exemplare);
        BookService.editBook(a, b, c, an, publicator, exemplare);
        assertThat(BookService.allBooks()).isNotEmpty();
        assertThat(BookService.allBooks()).size().isEqualTo(1);
        Book book = BookService.allBooks().get(0);
        assertThat(book).isNotNull();
        assertThat(book.getOwner()).isEqualTo(a);
        assertThat(book.getBookname()).isEqualTo(b);
        assertThat(book.getAuthor()).isEqualTo(c);
        assertThat(book.getYear()).isEqualTo(an);
        assertThat(book.getPublisher()).isEqualTo(publicator);
        assertThat(book.getNoEx()).isEqualTo(exemplare);
    }

    @Test
    void testAdminDeleteBook() throws BookExists{
        BookService.addBook(a, b, c, an, publicator, exemplare);
        BookService.adminDeleteBook(a,b);
        assertThat(BookService.allBooks()).isEmpty();
    }

    @Test
    void testUserDeleteBook() throws BookExists{
        BookService.addBook(a, b, c, an, publicator, exemplare);
        BookService.deleteBook(b,a);
        assertThat(BookService.Lista()).isEmpty();
    }
}