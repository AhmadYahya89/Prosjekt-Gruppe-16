package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import register_and_login.AuthService;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
    }

    @Test
    void testRegisterUser_Success() {
        boolean result = authService.registerUser("validUser", "strongPassword");
        assertTrue(result, "User should be successfully registered");
    }

    @Test
    void testRegisterUser_ExistingUser() {
        authService.registerUser("existingUser", "password123");
        boolean result = authService.registerUser("existingUser", "newPassword");
        assertFalse(result, "Registration should fail for an existing username");
    }

    @Test
    void testRegisterUser_InvalidUsername() {
        boolean result = authService.registerUser("abc", "password123");
        assertFalse(result, "Registration should fail for an invalid username");
    }

    @Test
    void testRegisterUser_InvalidPassword() {
        boolean result = authService.registerUser("validUser", "short");
        assertFalse(result, "Registration should fail for a password shorter than 8 characters");
    }

    @Test
    void testLoginUser_Success() {
        authService.registerUser("loginUser", "password123");
        boolean result = authService.loginUser("loginUser", "password123");
        assertTrue(result, "Login should succeed with correct username and password");
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        authService.registerUser("validUser", "password123");
        boolean result = authService.loginUser("validUser", "wrongPassword");
        assertFalse(result, "Login should fail with incorrect password");
    }

    @Test
    void testLoginUser_NonExistingUser() {
        boolean result = authService.loginUser("nonUser", "password123");
        assertFalse(result, "Login should fail for a non-existing user");
    }
}

