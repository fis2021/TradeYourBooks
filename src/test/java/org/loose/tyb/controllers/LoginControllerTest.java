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
import org.loose.tyb.exceptions.UsernameAlreadyExistsException;
import org.loose.tyb.services.BookService;
import org.loose.tyb.services.FileSystemService;
import org.loose.tyb.services.ReportService;
import org.loose.tyb.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    public static final String USER = "gigi";
    public static final String USERPASS = "gigi";
    public static final String ADMIN = "admin";
    public static final String ADMINPASS = "admin";

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
    void testUserLogin(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, USERPASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(USERPASS);
        robot.clickOn("#loginButton");
    }

    @Test
    void testAdminLogin(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(ADMIN, ADMINPASS);
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#loginButton");
    }

    @Test
    void testUserCanNotLogInWithoutCorrectCredentials(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, USERPASS);
        robot.clickOn("#usernameField");
        robot.write("user that dont exists");
        robot.clickOn("#passwordField");
        robot.write(USERPASS);
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#loginMessage").queryText()).hasText(String.format("Incorrect username or password!"));
    }

    @Test
    void testUserMustIntroduceAnUsername(FxRobot robot) throws UsernameAlreadyExistsException{
        UserService.addUser(USER, USERPASS);
        robot.clickOn("#passwordField");
        robot.write(USERPASS);
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#loginMessage").queryText()).hasText(String.format("Please type in a username!"));
    }

    @Test
    void testUserMustIntroduceAPassword(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, USERPASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#loginMessage").queryText()).hasText(String.format("Password cannot be empty"));
    }

    @Test
    void testGoToRegister(FxRobot robot) {
        robot.clickOn("#registerButton");
    }

}