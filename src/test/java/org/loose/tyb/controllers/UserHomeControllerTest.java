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
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)

class UserHomeControllerTest {

    public static final String USER = "gigi";
    public static final String PASS = "gigi";

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
    void userCanAddABookInHisLibrary(FxRobot robot) throws UsernameAlreadyExistsException {

        UserService.addUser(USER, PASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#Bookname");
        robot.write("2 Steps from hell");
        robot.clickOn("#Author");
        robot.write("Lucian Blaga");
        robot.clickOn("#Year");
        robot.write("1965");
        robot.clickOn("#Publisher");
        robot.write("Artemis");
        robot.clickOn("#noEx");
        robot.write("1");

        robot.clickOn("#addButton");
        assertThat(BookService.allBooks()).isNotEmpty();
    }

    @Test
    void userCanEditABook(FxRobot robot) throws UsernameAlreadyExistsException, BookExists {
        UserService.addUser(USER, PASS);
        BookService.addBook(USER,"Dacia", "Decebal", 700, "Roma(cred)", 700);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");

        robot.clickOn("#Bookname");
        robot.write("Dacia");
        robot.clickOn("#Author");
        robot.write("Marian");
        robot.clickOn("#Year");
        robot.write("1700");
        robot.clickOn("#Publisher");
        robot.write("Corint");
        robot.clickOn("#noEx");
        robot.write("2");

        robot.clickOn("#editButton");
        assertThat(BookService.allBooks()).isNotEmpty();
    }

    @Test
    void userCanDeleteBook(FxRobot robot) throws UsernameAlreadyExistsException, BookExists {
        UserService.addUser(USER, PASS);
        BookService.addBook(USER,"Dacia", "Decebal", 700, "Roma(cred)", 7);

        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#Bookname");
        robot.write("Dacia");

        robot.clickOn("#deleteButton");
        assertThat(BookService.allBooks()).isEmpty();
    }

    @Test
    void userCanGoToOnlineLibrary(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, PASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#LibPageButton");
    }

    @Test
    void userCanLogOut(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, PASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#logoutButton");
    }

    @Test
    void userCanNotAddTheSameBookTwice(FxRobot robot) throws UsernameAlreadyExistsException, BookExists {
        UserService.addUser(USER, PASS);
        BookService.addBook(USER,"Dacia", "Decebal", 700, "Roma(cred)", 7);

        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");

        robot.clickOn("#Bookname");
        robot.write("Dacia");
        robot.clickOn("#Author");
        robot.write("Decebal");
        robot.clickOn("#Year");
        robot.write("700");
        robot.clickOn("#Publisher");
        robot.write("Roma(cred)");
        robot.clickOn("#noEx");
        robot.write("7");

        robot.clickOn("#addButton");
        assertThat(robot.lookup("#TEXT").queryText()).hasText(
                String.format("Book already exists")
        );
    }

    @Test
    void userMustAddCorrectDataTypeWhenAddinABook(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, PASS);

        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");

        robot.clickOn("#Bookname");
        robot.write("Dacia");
        robot.clickOn("#Author");
        robot.write("Mihai");
        robot.clickOn("#Year");
        robot.write("namila");
        robot.clickOn("#Publisher");
        robot.write("Roma(cred)");
        robot.clickOn("#noEx");
        robot.write("7");

        robot.clickOn("#addButton");
        assertThat(robot.lookup("#TEXT").queryText()).hasText(
                String.format("Wrong data type")
        );
    }

    @Test
    void userMustAddCorrectDataTypeWhenEditingABook(FxRobot robot) throws UsernameAlreadyExistsException, BookExists {
        UserService.addUser(USER, PASS);
        BookService.addBook(USER,"Dacia", "Decebal", 700, "Roma(cred)", 700);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");

        robot.clickOn("#Bookname");
        robot.write("Dacia");
        robot.clickOn("#Author");
        robot.write("Marian");
        robot.clickOn("#Year");
        robot.write("Manele");
        robot.clickOn("#Publisher");
        robot.write("Corint");
        robot.clickOn("#noEx");
        robot.write("2");

        robot.clickOn("#editButton");
        assertThat(robot.lookup("#TEXT").queryText()).hasText(
                String.format("Wrong data type")
        );
        assertThat(BookService.allBooks()).isNotEmpty();
    }
}