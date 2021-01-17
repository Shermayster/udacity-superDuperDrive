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

    public void addCredential(CredentialForm credentialForm, String username) {
        int userId = userService.getUser(username).getUserId();
        String key = encryptionService.getSecuredKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);
        Credential newCredential = new Credential();
        newCredential.setUserId(userId);
        newCredential.setUrl(credentialForm.getUrl());
        newCredential.setPassword(encryptedPassword);
        newCredential.setKey(key);
        newCredential.setUsername(credentialForm.getUsername());
        credentialMapper.insertCredential(newCredential);

    }

   public String decryptPassword(Credential credential) {
        System.out.println("credential" + credential);
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
   }
}
