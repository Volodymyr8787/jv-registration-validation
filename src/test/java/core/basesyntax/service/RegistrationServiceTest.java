package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.MIN_AGE;
import static core.basesyntax.service.RegistrationServiceImpl.MIN_LOGIN_LENGTH;
import static core.basesyntax.service.RegistrationServiceImpl.MIN_PASSWORD_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User("validLogin", "validPassword", 20);
        User registered = registrationService.register(user);
        assertNotNull(registered);
        assertEquals("validLogin", registered.getLogin());
        assertEquals("validPassword", registered.getPassword());
    }

    @Test
    void register_boundaryValues_ok() {
        User user = new User("a".repeat(MIN_LOGIN_LENGTH),
                "b".repeat(MIN_PASSWORD_LENGTH), MIN_AGE);
        User actual = registrationService.register(user);
        assertNotNull(actual);
        assertEquals(MIN_LOGIN_LENGTH, actual.getLogin().length());
        assertEquals(MIN_PASSWORD_LENGTH, actual.getPassword().length());
        assertEquals(MIN_AGE, actual.getAge());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "1234567", 19);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("short", "password123", 20);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("123456", "12345", 20);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_underAge_notOk() {
        User user = new User("123456", "123456", 17);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("123456", null, 19);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("1234567", "12345678", null);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user = new User("123456789", "123456789", 22);
        User second = new User("123456789", "123456789", 22);
        Storage.people.add(user);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(second));
    }
}
