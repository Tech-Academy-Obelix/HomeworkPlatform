package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.config.logging.LogService;
import com.obelix.homework.platform.web.user.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("name") String name) {
        try {
            var fileToDownload = fileStorageService.getDownloadFile(name);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; name=\"" + name + "\" ")
                    .contentLength(fileToDownload.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new FileSystemResource(fileToDownload));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            fileStorageService.saveMultipleFiles(files);
            log.info("File uploaded successfully");
            return ResponseEntity.ok("Files uploaded successfully!");
        } catch (IOException e) {
            log.error("Upload failed!");
            return ResponseEntity.status(500).body("Failed to upload files.");
        }
    }
}

