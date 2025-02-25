package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.web.user.service.FileStorageService;
import com.obelix.homework.platform.web.user.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileManagerController {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private LogService log;

    @PostMapping("/upload-file")
    public boolean uploadFile(@RequestParam("file")MultipartFile file){
        try {
            fileStorageService.saveFile(file);
            log.info("File uploaded successfully: " + file.getOriginalFilename());
            return true;
        }catch (IOException e) {
            log.error("File upload failed: " + file.getOriginalFilename());
            log.error(e.toString());
        }
        return false;
    }
}

