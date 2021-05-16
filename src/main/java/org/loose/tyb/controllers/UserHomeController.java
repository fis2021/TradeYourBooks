package org.loose.tyb.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.tyb.exceptions.BookExists;
import org.loose.tyb.model.Book;
import org.loose.tyb.services.BookService;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class UserHomeController {

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, String> colBookName;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, Integer> colYear;

    @FXML
    private TableColumn<Book, String> colPublisher;

    @FXML
    private TextField Bookname;

    @FXML
    private TextField Author;

    @FXML
    private TextField Year;

    @FXML
    private TextField Publisher;

    @FXML
    private TableColumn<Book, Integer> colnoEx;

    @FXML
    private TextField noEx;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Text TEXT;

    @FXML
    private Button LibPageButton;

    public UserHomeController() {
    }

    @FXML
    public void initialize() {
        colBookName.setCellValueFactory(new PropertyValueFactory<>("Bookname"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
        colnoEx.setCellValueFactory(new PropertyValueFactory<>("noEx"));


        table.setItems(BookService.Lista());
    }

    public void handleAddButton(javafx.event.ActionEvent actionEvent) throws NumberFormatException, BookExists {
        try {
            BookService.addBook(LoginController.loggedInAcc,Bookname.getText(), Author.getText(), Integer.parseInt(Year.getText()), Publisher.getText(), Integer.parseInt(noEx.getText()));
            colBookName.setCellValueFactory(new PropertyValueFactory<>("Bookname"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
            colYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
            colPublisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
            colnoEx.setCellValueFactory(new PropertyValueFactory<>("noEx"));

            table.setItems(BookService.Lista());
            TEXT.setText("");
        }
        catch(NumberFormatException k)
        {
            TEXT.setText("Wrong data type");
        }
        catch(BookExists k)
        {
            TEXT.setText("Book already exists");
        }
    }
    public void handleEditButton(javafx.event.ActionEvent actionEvent) {
        try {

            BookService.editBook(LoginController.loggedInAcc, Bookname.getText(),Author.getText(),Integer.parseInt(Year.getText()), Publisher.getText(), Integer.parseInt(noEx.getText()));
            table.setItems(BookService.Lista());
        }
        catch (NumberFormatException k)
        {
            TEXT.setText("Wrong data type");
        }
    }
    public void handleDeleteButton(javafx.event.ActionEvent actionEvent) {
            BookService.deleteBook(Bookname.getText(), LoginController.loggedInAcc);
            table.setItems(BookService.Lista());
    }
    public void handlePastTradesButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleLogoutButton(javafx.event.ActionEvent actionEvent) {
        try{
            Stage stage = (Stage) TEXT.getScene().getWindow();
            Parent viewStudentsRoot = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Scene scene = new Scene(viewStudentsRoot, 300, 275);
            stage.setScene(scene);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void handleSeeRequestsButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleLibPageButton(javafx.event.ActionEvent actionEvent) {
        try{
            Stage stage = (Stage) TEXT.getScene().getWindow();
            Parent viewStudentsRoot = FXMLLoader.load(getClass().getClassLoader().getResource("LibHome.fxml"));
            Scene scene = new Scene(viewStudentsRoot, 950, 700);
            stage.setScene(scene);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
