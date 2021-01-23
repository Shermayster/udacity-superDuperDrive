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
        try{
            int noteId;
            if(noteForm.getNoteId() == null) {
                noteId = noteService.addNote(noteForm, username);
            } else {
                noteId = noteService.updateNote(noteForm);
            }
            if(noteId > 0) {
                model.addAttribute("success", true);
            } else {
                model.addAttribute("error", "an error accrue");
            }
            this.clearNoteForm(noteForm);
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "server error");
        }

        return "result";
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId, Model model) {
        try {
            int deletedId = noteService.deleteNote(noteId);
            if(deletedId > 0) {
                model.addAttribute("success", true);
            }  else {
                model.addAttribute("error", "error during save");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "server error");
        }
        return "result";

    }

    private void clearNoteForm(NoteForm noteForm) {
        noteForm.setNoteId(null);
        noteForm.setTitle("");
        noteForm.setDescription("");
    }



}
