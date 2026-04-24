package com.ecommerce.user_service.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("SecurityConfig Tests")
class SecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should create PasswordEncoder bean successfully")
    void testPasswordEncoderBeanCreation() {
        assertNotNull(passwordEncoder);
    }

    @Test
    @DisplayName("Should encode password correctly")
    void testPasswordEncoding() {
        String plainPassword = "myPassword123";
        String encodedPassword = passwordEncoder.encode(plainPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(plainPassword, encodedPassword);
    }

    @Test
    @DisplayName("Should match encoded password with original")
    void testPasswordMatching() {
        String plainPassword = "myPassword123";
        String encodedPassword = passwordEncoder.encode(plainPassword);

        assertTrue(passwordEncoder.matches(plainPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should not match incorrect password")
    void testPasswordMismatch() {
        String plainPassword = "myPassword123";
        String differentPassword = "differentPassword";
        String encodedPassword = passwordEncoder.encode(plainPassword);

        assertFalse(passwordEncoder.matches(differentPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should generate different hashes for same password")
    void testDifferentHashesForSamePassword() {
        String plainPassword = "myPassword123";
        String hash1 = passwordEncoder.encode(plainPassword);
        String hash2 = passwordEncoder.encode(plainPassword);

        assertNotEquals(hash1, hash2);
        assertTrue(passwordEncoder.matches(plainPassword, hash1));
        assertTrue(passwordEncoder.matches(plainPassword, hash2));
    }

    @Test
    @DisplayName("Should handle empty password")
    void testEmptyPassword() {
        String emptyPassword = "";
        String encodedPassword = passwordEncoder.encode(emptyPassword);

        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(emptyPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should handle password with special characters")
    void testPasswordWithSpecialCharacters() {
        String specialPassword = "P@ssw0rd!#$%^&*()_+-=[]{}|;:,.<>?";
        String encodedPassword = passwordEncoder.encode(specialPassword);

        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(specialPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should handle very long password")
    void testVeryLongPassword() {
        String longPassword = "a".repeat(100);
        String encodedPassword = passwordEncoder.encode(longPassword);

        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(longPassword, encodedPassword));
    }

     @Test
    @DisplayName("Should be instance of BCryptPasswordEncoder")
    void testPasswordEncoderType() {
        assertNotNull(passwordEncoder);
        assertEquals("org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder",
                passwordEncoder.getClass().getName());
    }

    @Test
    @DisplayName("Should handle null password gracefully")
    void testNullPasswordHandling() {
        assertThrows(IllegalArgumentException.class, () -> {
            passwordEncoder.encode(null);
        });
    }


}

