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
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button pastTradesButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button seeRequestsButton;

    @FXML
    private Text TEXT;

    @FXML
    private Button homePageButton;

    @FXML
    public void handleAddButton(ActionEvent event) {

    }

    @FXML
    public void handleDeleteButton(ActionEvent event) {

    }

    @FXML
    public void handleEditButton(ActionEvent event) {

    }

    @FXML
    public void handleHomePageButton(ActionEvent event) {

    }

    @FXML
    public void handleLogoutButton(ActionEvent event) {

    }

    @FXML
    public void handlePastTradesButton(ActionEvent event) {

    }

    @FXML
    public void handleSeeRequestsButton(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        colBookName.setCellValueFactory(new PropertyValueFactory<Book,String>("Bookname"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Book,String>("Author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Book,Integer>("Year"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<Book,String>("Publisher"));

        table.setItems(BookService.Lista());
    }

    public void handleAddButton(javafx.event.ActionEvent actionEvent) {
        try {
            BookService.addBook(Bookname.getText(), Author.getText(), Integer.parseInt(Year.getText()), Publisher.getText());
            colBookName.setCellValueFactory(new PropertyValueFactory<Book,String>("Bookname"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<Book,String>("Author"));
            colYear.setCellValueFactory(new PropertyValueFactory<Book,Integer>("Year"));
            colPublisher.setCellValueFactory(new PropertyValueFactory<Book,String>("Publisher"));

            table.setItems(BookService.Lista());
            TEXT.setText("");}
        catch(NumberFormatException k)
        {
            TEXT.setText("Wrong data type");
        }
    }
    public void handleEditButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleDeleteButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handlePastTradesButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleLogoutButton(javafx.event.ActionEvent actionEvent) {
        try{
            Stage stage = (Stage) TEXT.getScene().getWindow();
            Parent viewStudentsRoot = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Scene scene = new Scene(viewStudentsRoot, 600, 400);
            stage.setScene(scene);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void handleSeeRequestsButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleHomePageButton(javafx.event.ActionEvent actionEvent) {
    }
}
