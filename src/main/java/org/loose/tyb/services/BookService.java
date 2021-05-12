package org.loose.tyb.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.exceptions.UniqueConstraintException;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.tyb.controllers.LoginController;
import org.loose.tyb.exceptions.BookExists;
import org.loose.tyb.model.Book;

import java.util.Objects;

import static org.loose.tyb.services.FileSystemService.getPathToFile;

public class BookService {

    private static ObjectRepository<Book> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("TradeYB12.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(Book.class);
    }

    public static ObservableList<Book> Lista()
    {
        ObservableList<Book>list= FXCollections.observableArrayList();

        for (Book book : userRepository.find()) {
                list.add(book);
        }
        return list;
    }

    public static ObservableList<Book> Lista2()
    {
        ObservableList<Book>list= FXCollections.observableArrayList();

        for (Book book : userRepository.find()) {
            if(book.getOwner().equals(LoginController.loggedInAcc))
                list.add(book);
        }
        return list;
    }

    public static void addBook(String o, String bookname, String author, int year, String publisher, int ne)  throws BookExists{
        try {
            userRepository.insert(new Book(o, bookname, author, year, publisher, ne));
        }
        catch(UniqueConstraintException e){
            throw new BookExists();
        }
    }

    public static void editBook(String o, String Bookname, String Author, int Year, String Publisher, int ne)
    {
        for (Book k : userRepository.find()) {
            if(k.getBookname().equals(Bookname)){
                k.setOwner(o);
                k.setAuthor(Author);
                k.setYear(Year);
                k.setPublisher(Publisher);
                k.setNoEx(ne);
                userRepository.update(k);
            }
        }
    }

    public static void deleteVehicle(String Bookname) {
        for (Book k : userRepository.find()) {
            if(k.getBookname().equals(Bookname))
                userRepository.remove(k);
        }
    }
}