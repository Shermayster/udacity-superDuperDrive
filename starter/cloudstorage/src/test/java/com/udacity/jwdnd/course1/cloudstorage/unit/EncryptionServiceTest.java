package com.udacity.jwdnd.course1.cloudstorage.unit;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptionServiceTest {
    private static final int TEST_LIMIT = 1000;

    private EncryptionService encryptionService;
    @BeforeEach
    public void beforeAll() {
        encryptionService = new EncryptionService();
    }

    @Test
    public void encryptAndDecryptPassword() {
        String testPassValue = "test";
        String key = encryptionService.getSecuredKey();
        String encryptedPassword = encryptionService.encryptValue(testPassValue, key);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, key);
        assertEquals(testPassValue, decryptedPassword);
    }
}
