package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;

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
    private FileService fileService;
    private EncryptionService encryptionService;
    public HomeController(NoteService noteService, EncryptionService encryptionService,
                          CredentialsService credentialsService, FileService fileService) {
        this.noteService = noteService;
        this.encryptionService = encryptionService;
        this.credentialsService = credentialsService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getHome(Authentication authentication, NoteForm noteForm, CredentialForm credentialForm, FileForm fileForm, Model model) {
        String username = authentication.getName();
        model.addAttribute("notes", this.noteService.getUserNotes(username));
        model.addAttribute("credentials", this.credentialsService.getCredentials(username));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("files", fileService.getFiles(username));
        return "home";
    }
}
