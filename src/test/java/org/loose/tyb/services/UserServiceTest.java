package org.loose.tyb.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.tyb.exceptions.AccountExists;
import org.loose.tyb.exceptions.UsernameAlreadyExistsException;
import org.loose.tyb.model.User;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {

    public static final String ADMIN = "gigi";

    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @Test
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(UserService.Lista()).isNotNull();
        assertThat(UserService.Lista()).isEmpty();
    }

    @Test
    void testUserIsAddedToDatabase() throws UsernameAlreadyExistsException {
        UserService.addUser(ADMIN, ADMIN);
        assertThat(UserService.Lista()).isNotEmpty();
        assertThat(UserService.Lista()).size().isEqualTo(1);
        User user = UserService.Lista().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(ADMIN);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(ADMIN, ADMIN));
        assertThat(user.getUsername()).isEqualTo(ADMIN);
    }

    @Test
    void testUserCanNotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser(ADMIN, ADMIN);
            UserService.addUser(ADMIN, ADMIN);
        });
    }

    @Test
    void testUserIsAddedAndFound() throws UsernameAlreadyExistsException {
        UserService.addUser(ADMIN, ADMIN);
        assertThrows(AccountExists.class, () -> UserService.checkUsernameAndPassword(ADMIN, ADMIN));
    }

    @Test
    void testEncodePassword(){
        assertThat(UserService.encodePassword(ADMIN,ADMIN)).isEqualTo(UserService.encodePassword(ADMIN,ADMIN));
    }
}