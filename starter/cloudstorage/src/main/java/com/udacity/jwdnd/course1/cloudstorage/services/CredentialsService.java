package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private UserService userService;
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialsService(UserService userService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }
    public List<Credential> getCredentials(String username) {
        int userId = userService.getUser(username).getUserId();
        return credentialMapper.getCredentialsList(userId);
    }

    public int addCredential(CredentialForm credentialForm, String username) {
        int userId = userService.getUser(username).getUserId();
        String key = encryptionService.getSecuredKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);
        Credential newCredential = new Credential();
        newCredential.setUserId(userId);
        newCredential.setUrl(credentialForm.getUrl());
        newCredential.setPassword(encryptedPassword);
        newCredential.setKey(key);
        newCredential.setUsername(credentialForm.getUsername());
        return credentialMapper.insertCredential(newCredential);
    }

    public int updateCredential(CredentialForm credentialForm) {
        Credential credential = credentialMapper.getCredential(credentialForm.getCredentialId());
        credential.setUsername(credentialForm.getUsername());
        credential.setUrl(credentialForm.getUrl());
        if(!encryptionService.decryptValue(credential.getPassword(), credential.getKey()).equals(credentialForm.getPassword())) {
            String key = encryptionService.getSecuredKey();
            String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);
            credential.setPassword(encryptedPassword);
            credential.setKey(key);
        }
       return credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(int credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }
}
