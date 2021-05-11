package org.loose.tyb.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.tyb.model.Book;


import static org.loose.tyb.services.FileSystemService.getPathToFile;

public class BookService {

    private static ObjectRepository<Book> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("books.db").toFile())
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

}