package org.loose.tyb.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.tyb.model.Book;

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
    private TextField aAuthor;

    @FXML
    private Button adeleteButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Text TEXT;

    @FXML
    public void handleAdminDeleteButton(ActionEvent event) {

    }

    @FXML
    public void handleAdminReportsButton(ActionEvent event) {

    }

    @FXML
    public void handleLogoutButton(ActionEvent event) {

    }

