package org.loose.tyb.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.tyb.exceptions.AlreadyReported;
import org.loose.tyb.exceptions.BookExists;
import org.loose.tyb.exceptions.UsernameAlreadyExistsException;
import org.loose.tyb.services.BookService;
import org.loose.tyb.services.FileSystemService;
import org.loose.tyb.services.ReportService;
import org.loose.tyb.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ReportsControllerTest {
    @AfterEach
    void tearDown() {
        BookService.getDatabase().close();
        UserService.getDatabase().close();
        ReportService.getDatabase().close();
    }

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        BookService.initDatabase();
        ReportService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Test
    void adminCanDeleteAReport(FxRobot robot) throws UsernameAlreadyExistsException, AlreadyReported {
        UserService.addUser("admin", "admin");
        ReportService.ReportBook("Gigi", "Tanda", "Dont exists");
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#reportsButton");

        robot.clickOn("#arOwner");
        robot.write("Gigi");
        robot.clickOn("#arBookname");
        robot.write("Tanda");
        robot.clickOn("#arReason");
        robot.write("Dont exists");
        robot.clickOn("#adreportsButton");

        assertThat(ReportService.ReportList()).isEmpty();
    }

    @Test
    void adminCanDeleteABookFromReports(FxRobot robot) throws UsernameAlreadyExistsException, AlreadyReported, BookExists {
        UserService.addUser("admin", "admin");
        ReportService.ReportBook("Gigi", "Tanda", "Dont exists");
        BookService.addBook("Gigi", "Tanda", "Mari", 1960, "Corint", 2);
        assertThat(ReportService.ReportList()).isNotEmpty();
        assertThat(BookService.allBooks()).isNotEmpty();

        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#reportsButton");

        robot.clickOn("#arOwner");
        robot.write("Gigi");
        robot.clickOn("#arBookname");
        robot.write("Tanda");


        robot.clickOn("#ardeleteButton");
        assertThat(ReportService.ReportList()).isEmpty();
        assertThat(BookService.allBooks()).isEmpty();
    }

    @Test
    void adminCanGoBackToControlPanel(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser("admin", "admin");
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#reportsButton");
        robot.clickOn("#controlPanelButton");
    }

    @Test
    void adminNeeedsToIntrdouceOwnerToDeleteAReport(FxRobot robot) throws AlreadyReported, UsernameAlreadyExistsException {
        UserService.addUser("admin", "admin");
        ReportService.ReportBook("Gigi", "Tanda", "Dont exists");
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#reportsButton");

        robot.clickOn("#arBookname");
        robot.write("Tanda");
        robot.clickOn("#arReason");
        robot.write("Dont exists");
        robot.clickOn("#adreportsButton");
        Assertions.assertThat(robot.lookup("#TEXT").queryText()).hasText(String.format("Introduce an Owner!"));
    }

    @Test
    void adminNeedsToIntrdouceBookNameToDeleteAreport(FxRobot robot) throws UsernameAlreadyExistsException, AlreadyReported {
        UserService.addUser("admin", "admin");
        ReportService.ReportBook("Gigi", "Tanda", "Dont exists");
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#reportsButton");

        robot.clickOn("#arOwner");
        robot.write("Gigi");
        robot.clickOn("#arReason");
        robot.write("Dont exists");
        robot.clickOn("#adreportsButton");
        Assertions.assertThat(robot.lookup("#TEXT").queryText()).hasText(String.format("Introduce a book!!"));
    }

    @Test
    void adminNeeedsToIntrdouceOwnerToDeleteABookAndItsReport(FxRobot robot) throws AlreadyReported, UsernameAlreadyExistsException {
        UserService.addUser("admin", "admin");
        ReportService.ReportBook("Gigi", "Tanda", "Dont exists");
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#reportsButton");

        robot.clickOn("#arBookname");
        robot.write("Tanda");
        robot.clickOn("#arReason");
        robot.write("Dont exists");
        robot.clickOn("#ardeleteButton");
        Assertions.assertThat(robot.lookup("#TEXT").queryText()).hasText(String.format("Introduce an Owner!"));
    }

    @Test
    void adminNeeedsToIntrdouceABookNameToDeleteABookAndItsReport(FxRobot robot) throws AlreadyReported, UsernameAlreadyExistsException {
        UserService.addUser("admin", "admin");
        ReportService.ReportBook("Gigi", "Tanda", "Dont exists");
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#reportsButton");

        robot.clickOn("#arOwner");
        robot.write("Gigi");
        robot.clickOn("#arReason");
        robot.write("Dont exists");
        robot.clickOn("#ardeleteButton");
        Assertions.assertThat(robot.lookup("#TEXT").queryText()).hasText(String.format("Introduce a book!!"));
    }
}