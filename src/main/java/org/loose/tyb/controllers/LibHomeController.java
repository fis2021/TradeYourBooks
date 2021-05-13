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
import org.loose.tyb.exceptions.AlreadyReported;
import org.loose.tyb.model.Book;
import org.loose.tyb.services.LibService;
import org.loose.tyb.services.BookService;
import org.loose.tyb.services.ReportService;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LibHomeController {

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, String> colLibOwner;

    @FXML
    private TableColumn<Book, String> collLibBookName;

    @FXML
    private TableColumn<Book, String> colLibAuthor;

    @FXML
    private TableColumn<Book, Integer> colLibYear;

    @FXML
    private TableColumn<Book, String> colLibPublisher;

    @FXML
    private TableColumn<Book, Integer> colLibnoEx;

    @FXML
    private TextField libBookname;

    @FXML
    private TextField libOwner;

    @FXML
    private Button reportButton;

    @FXML
    private Button tradeButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Text TEXT;

    @FXML
    private Button homePageButton;

    @FXML
    private TextField libRR;

    @FXML
    public void handleHomePageButton(ActionEvent event) {

    }

    @FXML
    public void handleLogoutButton(ActionEvent event) {

    }

    @FXML
    public void handleOfferTradeButton(ActionEvent event) {

    }

    @FXML
    public void handleReportButton(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        colLibOwner.setCellValueFactory(new PropertyValueFactory<Book,String>("Owner"));
        collLibBookName.setCellValueFactory(new PropertyValueFactory<Book,String>("Bookname"));
        colLibAuthor.setCellValueFactory(new PropertyValueFactory<Book,String>("Author"));
        colLibYear.setCellValueFactory(new PropertyValueFactory<Book,Integer>("Year"));
        colLibPublisher.setCellValueFactory(new PropertyValueFactory<Book,String>("Publisher"));
        colLibnoEx.setCellValueFactory(new PropertyValueFactory<Book,Integer>("noEx"));


        table.setItems(BookService.ListaLib());
    }
    public void handleReportButton(javafx.event.ActionEvent actionEvent) {
        try{
        ReportService.ReportBook(libOwner.getText(), libBookname.getText(), libRR.getText());
        TEXT.setText("Book reported");
        }
        catch(NumberFormatException k){
            TEXT.setText("Please introduce correct name!");
        }
        catch(AlreadyReported k)
        {
            TEXT.setText("Already reported for that reason!");
        }
    }
    public void handleOfferTradeButton(javafx.event.ActionEvent actionEvent) {
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
    public void handleHomePageButton(javafx.event.ActionEvent actionEvent) {
        try{
            Stage stage = (Stage) TEXT.getScene().getWindow();
            Parent viewStudentsRoot = FXMLLoader.load(getClass().getClassLoader().getResource("userHome.fxml"));
            Scene scene = new Scene(viewStudentsRoot, 900, 700);
            stage.setScene(scene);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}