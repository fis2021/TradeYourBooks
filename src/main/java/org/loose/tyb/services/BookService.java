package org.loose.tyb.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.exceptions.UniqueConstraintException;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.tyb.controllers.LoginController;
import org.loose.tyb.exceptions.BookExists;
import org.loose.tyb.model.Book;

import static org.loose.tyb.services.FileSystemService.getPathToFile;

public class BookService {

    public static ObjectRepository<Book> bookRepository;
    private static Nitrite database;

    public static void initDatabase() {
        database = Nitrite.builder()
                .filePath(getPathToFile("TradeYB12.db").toFile())
                .openOrCreate("test", "test");

        bookRepository = database.getRepository(Book.class);
    }

    public static void closedatabase(){
        database.close();
    }


    public static ObservableList<Book> Lista() {
        ObservableList<Book> list = FXCollections.observableArrayList();

        for (Book book : bookRepository.find()) {
            if (book.getOwner().equals(LoginController.loggedInAcc))
                list.add(book);
        }
        return list;
    }

    public static ObservableList<Book> ListaLib() {
        ObservableList<Book> list = FXCollections.observableArrayList();

        for (Book book : bookRepository.find()) {
            if (!book.getOwner().equals(LoginController.loggedInAcc))
                list.add(book);
        }
        return list;
    }

    public static ObservableList<Book> allBooks() {
        ObservableList<Book> list = FXCollections.observableArrayList();
        for (Book book : bookRepository.find()) {
            list.add(book);
        }
        return list;
    }

    public static void addBook(String o, String bookname, String author, int year, String publisher, int ne) throws BookExists {
        try {
            bookRepository.insert(new Book(o, bookname, author, year, publisher, ne));
        } catch (UniqueConstraintException e) {
            throw new BookExists();
        }
    }

    public static void editBook(String o, String Bookname, String Author, int Year, String Publisher, int ne) {
        for (Book k : bookRepository.find()) {
            if (k.getBookname().equals(Bookname) && k.getOwner().equals(o)) {
                k.setOwner(o);
                k.setAuthor(Author);
                k.setYear(Year);
                k.setPublisher(Publisher);
                k.setNoEx(ne);
                bookRepository.update(k);
            }
        }
    }

    public static void deleteBook(String Bookname, String o) {
        for (Book k : bookRepository.find()) {
            if (k.getBookname().equals(Bookname) && (k.getOwner().equals(LoginController.loggedInAcc) || k.getOwner().equals(o)))
                bookRepository.remove(k);

        }
    }

    public static void adminDeleteBook(String Owner, String Bookname)
    {
        for(Book k : bookRepository.find()){
            if(k.getBookname().equals(Bookname) && k.getOwner().equals(Owner)){
                bookRepository.remove(k);
            }
        }
    }

    public static Nitrite getDatabase(){return database;}
}