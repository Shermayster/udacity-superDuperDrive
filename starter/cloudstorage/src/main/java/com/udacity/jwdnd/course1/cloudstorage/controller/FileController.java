package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/file/upload")
    String uploadFile(Authentication authentication, FileForm fileForm, Model model) {
        String username = authentication.getName();
        String errorMsg = null;
        String result = null;
        MultipartFile file = fileForm.getFile();

        if(file.isEmpty()) {
            errorMsg = "file is empty";
        }

        if(file.getSize() > 10000) {
            errorMsg = "File is too big. Choose lower file";
        }

        if(fileService.isExist(file)) {
            errorMsg = "File already exist";
        }

        if(errorMsg == null) {
            result = fileService.uploadFile(fileForm, username);
        }
        if(result == null) {
            model.addAttribute("error", errorMsg);
        } else if(result.equals("success")) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", result);
        }
        return "result";
    }

    @GetMapping("/file/{fileId}")
    @ResponseBody
    public ResponseEntity<byte[]> serveFile(@PathVariable int fileId) {
        File file = fileService.getFile(fileId);
        if(file != null) {
            String fileName = file.getFileName();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"")
                    .body(file.getFileData());
        }

     return null;
    }

    @GetMapping("file/delete/{fileId}")
    public String deleteFile(@PathVariable int fileId, Model model) {
        String errorMsg = null;
        if(fileId > 0) {
            int deletedId = fileService.deleteFile(fileId);
            if(deletedId < 1) {
                errorMsg = "the file was not found";
            }
        }

        if(errorMsg == null) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", errorMsg);
        }
        return "result";
    }

}
