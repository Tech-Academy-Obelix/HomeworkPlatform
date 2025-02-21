package com.obelix.homework.platform.web.admin.controller;

import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.web.admin.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

//    @Value("${file.upload-dir}")
    private String uploadDir;


    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/subjects/{id}")
    public Subject getSubject(@PathVariable UUID id) {
        return subjectService.getSubjectById(id);
    }

    @PostMapping("/subjects/{subjectName}")
    public Subject createSubject(@PathVariable String subjectName) {
        return subjectService.createSubject(subjectName);
    }

    @DeleteMapping("subjects/{id}")
    public void deleteSubject(@PathVariable UUID id) {
        subjectService.deleteSubjectById(id);
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "File uploaded successfully: " + file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}
