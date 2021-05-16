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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class adminHomeControllerTest {
    @AfterEach
    void tearDown() {
        BookService.getDatabase().close();
        UserService.getDatabase().close();
        ReportService.getDatabase().close();
    }

    @BeforeEach
    void setUp(FxRobot robot) throws Exception{
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
    void adminCanLogOut(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser("admin", "admin");
        assertThat(UserService.Lista()).isNotEmpty();
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#logoutButton");
    }

    @Test
    void adminCanSeeAllreports(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser("admin", "admin");
        assertThat(UserService.Lista()).isNotEmpty();
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#reportsButton");
    }

    @Test
    void adminCanDeleteABook(FxRobot robot) throws UsernameAlreadyExistsException, BookExists {
        BookService.addBook("gigi", "Tandy", "Mirel", 2009, "Catena", 2);
        assertThat(BookService.allBooks()).isNotEmpty();

        UserService.addUser("admin", "admin");
        assertThat(UserService.Lista()).isNotEmpty();
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#aOwner");
        robot.write("gigi");
        robot.clickOn("#aBookname");
        robot.write("Tandy");
        robot.clickOn("#adeleteButton");
        assertThat(BookService.allBooks()).isEmpty();
    }

    @Test
    void adminMustSelectTheBookToDeleteABook(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser("admin", "admin");
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");

        robot.clickOn("#aOwner");
        robot.write("gigi");
        robot.clickOn("#adeleteButton");

        Assertions.assertThat(robot.lookup("#TEXT").queryText()).hasText(String.format("Introduce a book!!"));
    }

    @Test
    void adminMustSelectTheOwnerToDeleteABook(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser("admin", "admin");
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");

        robot.clickOn("#aBookname");
        robot.write("Tandy");
        robot.clickOn("#adeleteButton");

        Assertions.assertThat(robot.lookup("#TEXT").queryText()).hasText(String.format("Introduce an Owner!"));
    }
}