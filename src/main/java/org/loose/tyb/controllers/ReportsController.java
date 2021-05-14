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
import org.loose.tyb.model.Report;
import org.loose.tyb.services.BookService;
import org.loose.tyb.services.ReportService;

import java.io.IOException;

public class ReportsController {

        @FXML
        private TableView<Report> table;

        @FXML
        private TableColumn<Report, String> rcolOwner;

        @FXML
        private TableColumn<Report, String> rcolBookName;

        @FXML
        private TableColumn<Report, String> rcolReason;

        @FXML
        private TextField arBookname;

        @FXML
        private TextField arOwner;

        @FXML
        private TextField arReason;

        @FXML
        private Button ardeleteButton;

        @FXML
        private Button adreportsButton;

        @FXML
        private Button controlPanelButton;

        @FXML
        private Text TEXT;

        @FXML
        public void handleControlPanelButton(ActionEvent event) {
                try{
                        Stage stage = (Stage) TEXT.getScene().getWindow();
                        Parent viewStudentsRoot = FXMLLoader.load(getClass().getClassLoader().getResource("adminHome.fxml"));
                        Scene scene = new Scene(viewStudentsRoot, 980, 700);
                        stage.setScene(scene);
                }catch(IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void handleRepDeleteBookButton(ActionEvent event) {
                String booknameText = arBookname.getText();
                String owner = arOwner.getText();

                if (booknameText == null || booknameText.isEmpty()) {
                        TEXT.setText("Introduce a book!!");
                        return;
                }

                if (owner == null || owner.isEmpty()) {
                        TEXT.setText("Introduce an Owner!");
                        return;
                }
                ReportService.deleteBookReport(arOwner.getText(), arBookname.getText());
                table.setItems(ReportService.ReportList());
        }

        @FXML
        public void handleReportDeleteButton(ActionEvent event) {
                String booknameText = arBookname.getText();
                String owner = arOwner.getText();

                if (booknameText == null || booknameText.isEmpty()) {
                        TEXT.setText("Introduce a book!!");
                        return;
                }

                if (owner == null || owner.isEmpty()) {
                        TEXT.setText("Introduce an Owner!");
                        return;
                }
                ReportService.deleteReport(arOwner.getText(), arBookname.getText(), arReason.getText());
                table.setItems(ReportService.ReportList());
        }

        @FXML
        public void initialize() {
                rcolOwner.setCellValueFactory(new PropertyValueFactory<Report, String>("Owner"));
                rcolBookName.setCellValueFactory(new PropertyValueFactory<Report, String>("Bookname"));
                rcolReason.setCellValueFactory(new PropertyValueFactory<Report, String>("Reason"));

                table.setItems(ReportService.ReportList());
        }
}
