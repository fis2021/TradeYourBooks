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
import org.loose.tyb.services.FileSystemService;
import org.loose.tyb.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)

class RegistrationControllerTest {


        public static final String USER = "user";
        public static final String PASS = "pass";

        @BeforeEach
        void setUp() throws Exception {
            FileSystemService.APPLICATION_FOLDER = ".test-registration";
            FileSystemService.initDirectory();
            FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
            UserService.initDatabase();
        }

    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        primaryStage.setTitle("Registration Example");
        primaryStage.setScene(new Scene(root, 400, 275));
        primaryStage.show();
    }

    @Test
    void testRegisterUser(FxRobot robot) {
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);
        robot.clickOn("#registerButton");
        assertThat(UserService.Lista().size()).isEqualTo(1);
    }

    @Test
    void testCantRegisterSameUser(FxRobot robot) throws UsernameAlreadyExistsException {
        UserService.addUser(USER, PASS);
        robot.clickOn("#usernameField");
        robot.write(USER);
        robot.clickOn("#passwordField");
        robot.write(PASS);

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText(
                String.format("An account with the username %s already exists!", USER)
        );
        assertThat(UserService.Lista().size()).isEqualTo(1);
    }

    @Test
    void testGoToRegister(FxRobot robot) {
        robot.clickOn("#loginButton");
    }


}