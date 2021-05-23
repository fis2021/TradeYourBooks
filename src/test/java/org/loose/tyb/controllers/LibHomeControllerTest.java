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
class LibHomeControllerTest {

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
    void userCanReportABook(FxRobot robot) throws UsernameAlreadyExistsException, BookExists {
        UserService.addUser(USER, PASS);
        BookService.addBook("Marian", "Testul", "Minoi", 2020,"Corint",1);
        assertThat(BookService.allBooks()).isNotEmpty();
        assertThat(UserService.Lista()).isNotEmpty();
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#LibPageButton");

        robot.clickOn("#libBookname");
        robot.write("Testul");
        robot.clickOn("#libOwner");
        robot.write("Marian");
        robot.clickOn("#libRR");
        robot.write("De jmeker");
        robot.clickOn("#reportButton");
        assertThat(ReportService.ReportList()).isNotEmpty();
        Assertions.assertThat(robot.lookup("#TEXT").queryText()).hasText(String.format("Book reported"));
    }

    @Test
    void userNeedToCompleteAllCampsToReportABook(FxRobot robot) throws UsernameAlreadyExistsException, BookExists {
        UserService.addUser(USER, PASS);
        BookService.addBook("Marian", "Testul", "Minoi", 2020,"Corint",1);
        assertThat(BookService.allBooks()).isNotEmpty();
        assertThat(UserService.Lista()).isNotEmpty();
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#LibPageButton");

        robot.clickOn("#libBookname");
        robot.write("Testul");
        robot.clickOn("#libOwner");
        robot.write("Marian");
        robot.clickOn("#reportButton");
        Assertions.assertThat(robot.lookup("#TEXT").queryText()).hasText(String.format("All camps should be completed"));
    }

    @Test
    void userCanLogOutFromHere(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, PASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#logoutButton");
    }

    @Test
    void userCanGoToHisLibrary(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, PASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#LibPageButton");
        robot.clickOn("#homePageButton");
    }


    @Test
    void userCantReportABookForTheSameReasonItWasReportedAlready(FxRobot robot) throws AlreadyReported, UsernameAlreadyExistsException, BookExists {
        BookService.addBook("Adi", "Colt alt", "Marian", 1998, "Dani", 1);
        ReportService.ReportBook("Adi", "Colt alt", "Wrong title");
        UserService.addUser(USER, PASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#LibPageButton");

        robot.clickOn("#libBookname");
        robot.write("Colt alt");
        robot.clickOn("#libOwner");
        robot.write("Adi");
        robot.clickOn("#libRR");
        robot.write("Wrong title");
        robot.clickOn("#reportButton");

        Assertions.assertThat(robot.lookup("#TEXT").queryText()).hasText(String.format("Already reported for that reason!"));
    }

    @Test
    void userCanLogOutFromLibHome(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, PASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#loginButton");
        robot.clickOn("#LibPageButton");
        robot.clickOn("#logoutButton");
    }

    @Test
    void userCanSearchForABook(FxRobot TalpaJr) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, PASS);
        TalpaJr.clickOn("#usernameField");
        TalpaJr.write(USER);
        TalpaJr.clickOn("#passwordField");
        TalpaJr.write(PASS);
        TalpaJr.clickOn("#loginButton");
        TalpaJr.clickOn("#LibPageButton");
        TalpaJr.clickOn("#seelib");
        TalpaJr.clickOn();
        TalpaJr.write("gigi");
        TalpaJr.clickOn("Search");
        assertThat(TalpaJr.lookup("Ownerul cautat nu exista!!!"));
    }

}