package org.loose.tyb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.loose.tyb.model.Book;
import org.loose.tyb.services.BookService;

import java.awt.event.ActionEvent;

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
        colBookName.setCellValueFactory(new PropertyValueFactory<Book,String>("Book name"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Book,String>("Author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Book,Integer>("Year"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<Book,String>("numberOfVehicles"));

        table.setItems(BookService.Lista());
    }

    public void handleAddButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleEditButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleDeleteButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handlePastTradesButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleLogoutButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleSeeRequestsButton(javafx.event.ActionEvent actionEvent) {
    }
    public void handleHomePageButton(javafx.event.ActionEvent actionEvent) {
    }
}
