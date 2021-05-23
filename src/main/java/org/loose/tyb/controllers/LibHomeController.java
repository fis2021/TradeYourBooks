package org.loose.tyb.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.tyb.exceptions.AlreadyReported;
import org.loose.tyb.model.Book;
import org.loose.tyb.model.TradeOffer;
import org.loose.tyb.services.BookService;
import org.loose.tyb.services.ReportService;

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

    public void handleOfferTradeButton(javafx.event.ActionEvent event) throws IOException {
        StackPane secondaryLayout1 = new StackPane();
        Label textlabel = new Label("Trade sent succesfully!");
        secondaryLayout1.getChildren().add(textlabel);
        Scene secondScene1 = new Scene(secondaryLayout1, 600, 400);
        Stage stage = new Stage();
        TradeOffer trade = new TradeOffer(libOwner.getText(), colLibOwner.getText(), libBookname.getText(), collLibBookName.getText());
        stage.setTitle("TRADE SENT");
        stage.setScene(secondScene1);
        stage.show();
    }

    public void handleSeeUserLib(javafx.event.ActionEvent event) throws IOException{
        StackPane secondaryLayout1 = new StackPane();
        Label textlabel = new Label("Choose the user whose lib you want to see");
        TextField textie = new TextField();
        Button buton = new Button("Search");
        secondaryLayout1.getChildren().add(textie);
        secondaryLayout1.getChildren().add(textlabel);
        secondaryLayout1.getChildren().add(buton);
        buton.setTranslateY(-100);
        textlabel.setTranslateY(100);
        Scene secondScene1 = new Scene(secondaryLayout1, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Choose user to see the lib");
        stage.setScene(secondScene1);
        stage.show();
        buton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                stage.close();
                String owner = textie.getText();
                //System.out.println(carte);
                BookService.closedatabase();
                BookService.initDatabase();
                String afisare = " ";
                for(Book t : BookService.bookRepository.find())
                    if(t.getOwner().equals(owner)){
                        afisare = afisare + t.getBookname() + " by " + t.getAuthor() + "\n" + t.getPublisher() + "\n" + t.getYear() + "\n" + t.getOwner() + "\n" + t.getNoEx() +"\n";
                    }
                if(!afisare.equals(" ")) {
                    StackPane secondaryLayout1 = new StackPane();
                    Label write = new Label();
                    write.setText(afisare);
                    secondaryLayout1.getChildren().add(write);
                    Scene secondScene1 = new Scene(secondaryLayout1, 600, 400);
                    Stage newWindow1 = new Stage();
                    newWindow1.setTitle("The searched books from that owner");
                    newWindow1.setScene(secondScene1);
                    newWindow1.show();
                }
                else {
                    StackPane secondaryLayout1 = new StackPane();
                    Label write = new Label("Ownerul cautat nu exista!!!");
                    secondaryLayout1.getChildren().add(write);
                    Scene secondScene1 = new Scene(secondaryLayout1, 600, 400);
                    Stage newWindow1 = new Stage();
                    newWindow1.setTitle("Ownerul nu exista");
                    newWindow1.setScene(secondScene1);
                    newWindow1.show();
                }
            }
        });
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
        String bookname = libBookname.getText();
        String owner = libOwner.getText();
        String reason = libRR.getText();

        if(bookname == null || bookname.isEmpty()  || owner == null || owner.isEmpty() || reason == null || reason.isEmpty())
        {
            TEXT.setText("All camps should be completed");
            return;
        }

        try{
            ReportService.ReportBook(libOwner.getText(), libBookname.getText(), libRR.getText());
            TEXT.setText("Book reported");
        }
        catch(AlreadyReported k)
        {
            TEXT.setText("Already reported for that reason!");
        }
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