package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NotesController {
    private NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping("/note")
    public String postNoteMessage(Authentication authentication, NoteForm noteForm, Model model) {
        String username = authentication.getName();
        if(noteForm.getNoteId() == null) {
            this.noteService.addNote(noteForm, username);
        } else {
            this.noteService.updateNote(noteForm);
        }
        this.clearNoteForm(noteForm);
        model.addAttribute("notes", noteService.getUserNotes(username));
        return "redirect:/home";
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId) {
        this.noteService.deleteNote(noteId);
        return "redirect:/home";
    }

    private void clearNoteForm(NoteForm noteForm) {
        noteForm.setNoteId(null);
        noteForm.setTitle("");
        noteForm.setDescription("");
    }



}
