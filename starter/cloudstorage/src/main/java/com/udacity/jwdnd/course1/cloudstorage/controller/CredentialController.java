package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CredentialController {
    private CredentialsService credentialsService;
    public CredentialController(CredentialsService credentialsService) { this.credentialsService = credentialsService;}

    @PostMapping("/add-credential")
    public String postCredentials(Authentication authentication, CredentialForm credentialForm, Model model) {
        String username = authentication.getName();
        this.credentialsService.addCredential(credentialForm, username);
        credentialForm.setCredentialId(null);
        credentialForm.setUrl("");
        credentialForm.setPassword("");
        credentialForm.setUsername("");
        List<Credential> credentialList = credentialsService.getCredentials(username);

        model.addAttribute("credentials", credentialList);
        return "redirect:home";
    }

    @PostMapping("/password")
    @ResponseBody
    public ResponseEntity<String> getPassword(Credential credential) {
        System.out.println("decript");
        try {
            return new ResponseEntity<String>(credentialsService.decryptPassword(credential), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
