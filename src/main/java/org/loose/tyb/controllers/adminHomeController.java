package org.loose.tyb.controllers;

import javafx.event.ActionEvent;
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

import java.io.IOException;

public class adminHomeController {

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, String> acolOwner;

    @FXML
    private TableColumn<Book, String> acolBookName;

    @FXML
    private TableColumn<Book, String> acolAuthor;

    @FXML
    private TableColumn<Book, Integer> acolYear;

    @FXML
    private TableColumn<Book, String> acolPublisher;

    @FXML
    private TableColumn<Book, Integer> acolnoEx;

    @FXML
    private TextField aBookname;

    @FXML
    private TextField aOwner;

    @FXML
    private Button adeleteButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Text TEXT;

    @FXML
    public void handleAdminReportsButton(ActionEvent event) {
        try{
            Stage stage = (Stage) TEXT.getScene().getWindow();
            Parent viewStudentsRoot = FXMLLoader.load(getClass().getClassLoader().getResource("Reports.fxml"));
            Scene scene = new Scene(viewStudentsRoot, 850, 700);
            stage.setScene(scene);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogoutButton(ActionEvent event) {
        try{
            Stage stage = (Stage) TEXT.getScene().getWindow();
            Parent viewStudentsRoot = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Scene scene = new Scene(viewStudentsRoot, 300, 275);
            stage.setScene(scene);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        acolOwner.setCellValueFactory(new PropertyValueFactory<>("Owner"));
        acolBookName.setCellValueFactory(new PropertyValueFactory<>("Bookname"));
        acolAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        acolYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
        acolPublisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
        acolnoEx.setCellValueFactory(new PropertyValueFactory<>("noEx"));

        table.setItems(BookService.allBooks());
    }

    public void handleAdminDeleteButton(ActionEvent actionEvent) {
        String booknameText = aBookname.getText();
        String owner = aOwner.getText();

        if (booknameText == null || booknameText.isEmpty()) {
            TEXT.setText("Introduce a book!!");
            return;
        }

        if (owner == null || owner.isEmpty()) {
            TEXT.setText("Introduce an Owner!");
            return;
        }
        BookService.adminDeleteBook(aOwner.getText(), aBookname.getText());
        table.setItems(BookService.allBooks());
    }
}
