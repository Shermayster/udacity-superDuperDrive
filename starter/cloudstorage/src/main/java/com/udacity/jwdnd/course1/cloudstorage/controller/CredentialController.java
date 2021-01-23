package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CredentialController {
    private CredentialsService credentialsService;
    public CredentialController(CredentialsService credentialsService) { this.credentialsService = credentialsService;}

    @PostMapping("/add-credential")
    public String postCredentials(Authentication authentication, CredentialForm credentialForm, Model model) {
        int credentialId;
        String username = authentication.getName();
        try{
            if(credentialForm.getCredentialId() == null) {
                credentialId = credentialsService.addCredential(credentialForm, username);
            } else {
                credentialId = credentialsService.updateCredential(credentialForm);
            }
            if(credentialId > 0) {
                model.addAttribute("success", true);
            } else {
                model.addAttribute("error", "an error accrue");
            }
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "server error");

        }
        return "result";
    }

    @GetMapping("/delete-credential/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") int credentialId, Model model) {
        try {
            this.credentialsService.deleteCredential(credentialId);
            model.addAttribute("success", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", e);
        }
        return "result";
    }

}
