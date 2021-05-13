package org.loose.tyb.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.loose.tyb.model.Book;
import org.loose.tyb.model.Report;
import org.loose.tyb.services.BookService;
import org.loose.tyb.services.ReportService;

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
        private Button ardeleteButton;

        @FXML
        private Button adreportsButton;

        @FXML
        private Button controlPanelButton;

        @FXML
        private Text TEXT;

        @FXML
        void handleControlPanelButton(ActionEvent event) {

        }

        @FXML
        void handleRepDeleteBookButton(ActionEvent event) {

        }

        @FXML
        void handleReportDeleteButton(ActionEvent event) {

        }

        @FXML
        public void initialize() {
                rcolOwner.setCellValueFactory(new PropertyValueFactory<Report, String>("Owner"));
                rcolBookName.setCellValueFactory(new PropertyValueFactory<Report, String>("Bookname"));
                rcolReason.setCellValueFactory(new PropertyValueFactory<Report, String>("Reason"));

                table.setItems(ReportService.ReportList());
        }
}
