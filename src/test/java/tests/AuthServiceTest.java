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

    //Krav: Brukeren skal kunne registrer seg med gyldige innloggingsdetaljer (gyldig brukenavn og passord).
    @Test
    void testRegisterUser_Success() {
        boolean result = authService.registerUser("validUser", "strongPassword");
        assertTrue(result, "User should be successfully registered");
    }

    //Krav: Systemet skal forhindre at eksisterende brukernavn resgisteres på nytt.
    @Test
    void testRegisterUser_ExistingUser() {
        authService.registerUser("existingUser", "password123");
        boolean result = authService.registerUser("existingUser", "newPassword");
        assertFalse(result, "Registration should fail for an existing username");
    }

    /*Krav: Brukernavnet må oppfylle visse kriterier(f.eks minimum lengde,
    ingen spesialtegn. osv.). Hvis det er ugyldig, skal registeringen feile*/
    @Test
    void testRegisterUser_InvalidUsername() {
        boolean result = authService.registerUser("abc", "password123");
        assertFalse(result, "Registration should fail for an invalid username");
    }
    /*Krav: Passoredt må være tilstrekkelig sterkt(f.eks. minimum 8 tegn).
      Hvis passordet er for kort eller svakt, skal registreringen feile.*/
    @Test
    void testRegisterUser_InvalidPassword() {
        boolean result = authService.registerUser("validUser", "short");
        assertFalse(result, "Registration should fail for a password shorter than 8 characters");
    }
    //Krav: Brukeren skal kunne logge inn med riktige detaljer (brukernavn og passord).
    @Test
    void testLoginUser_Success() {
        authService.registerUser("loginUser", "password123");
        boolean result = authService.loginUser("loginUser", "password123");
        assertTrue(result, "Login should succeed with correct username and password");
    }
    //Krav: Brukeren skal ikke kunne logge inn med feil passord.
    @Test
    void testLoginUser_InvalidCredentials() {
        authService.registerUser("validUser", "password123");
        boolean result = authService.loginUser("validUser", "wrongPassword");
        assertFalse(result, "Login should fail with incorrect password");
    }
    //Krav: Brukeren skal ikke kunne logge inn hvis de ikke eksisterer i systemet.
    @Test
    void testLoginUser_NonExistingUser() {
        boolean result = authService.loginUser("nonUser", "password123");
        assertFalse(result, "Login should fail for a non-existing user");
    }
}

