package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;
    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<Note> getUserNotes(String username) {
        int userId = this.userService.getUser(username).getUserId();
        return  this.noteMapper.getUserNotes(userId);
    }

    public int addNote(NoteForm noteForm, String username) {
        Note newNote = new Note();
        int userId = this.userService.getUser(username).getUserId();
        newNote.setNoteTitle(noteForm.getTitle());
        newNote.setNoteDescription(noteForm.getDescription());
        newNote.setUserId(userId);
       return noteMapper.insertNote(newNote);
    }

    public int updateNote(NoteForm noteForm) {
        Note updatedNote = new Note();
        updatedNote.setNoteTitle(noteForm.getTitle());
        updatedNote.setNoteId(noteForm.getNoteId());
        updatedNote.setNoteDescription(noteForm.getDescription());
        return noteMapper.updateNote(updatedNote);
    }

    public int deleteNote(int noteId){
        return noteMapper.deleteNote(noteId);
    }
}
