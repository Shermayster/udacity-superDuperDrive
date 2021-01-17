package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteService noteService;
    private CredentialsService credentialsService;
    public HomeController(NoteService noteService, CredentialsService credentialsService) {
        this.noteService = noteService;
        this.credentialsService = credentialsService;
    }

    @GetMapping
    public String getHome(Authentication authentication, NoteForm noteForm, CredentialForm credentialForm, Model model) {
        String username = authentication.getName();
        model.addAttribute("notes", this.noteService.getUserNotes(username));
        model.addAttribute("credentials", this.credentialsService.getCredentials(username));
        model.addAttribute("credentialsService", credentialsService);
        return "home";
    }
}
