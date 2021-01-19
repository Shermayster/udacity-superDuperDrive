package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {

    private UserService userService;
    private FileMapper fileMapper;

    public FileService(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles(String usename) {
        return fileMapper.getFiles(userService.getUser(usename).getUserId());
    }

    public String uploadFile(FileForm fileForm, String username) {
        try {
            this.fileMapper.uploadFile(convertFile(fileForm, username));
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "serverError";
        }

    }

    public File getFile(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public int deleteFile(int fileId) {
        return fileMapper.deleteFile(fileId);
    }

    public File getFileByName(String fileName) {
        return fileMapper.getFileByName(fileName);
    }

    public boolean isExist(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        File fileInDB = getFileByName(fileName);
        return fileInDB != null;
    }

    private File convertFile(FileForm fileForm, String username) throws IOException {
        MultipartFile file = fileForm.getFile();
        File newFile = new File();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        newFile.setFileName(fileName);
        newFile.setContentType(file.getContentType());
        newFile.setFileSize(file.getSize());
        newFile.setUserId(userService.getUser(username).getUserId());
        newFile.setFileData(file.getBytes());
        return newFile;
    }
}
