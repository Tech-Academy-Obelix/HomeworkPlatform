package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.model.dto.SubmittedHomeworkAssignmentDto;
import com.obelix.homework.platform.model.entity.domain.Grade;
import com.obelix.homework.platform.model.entity.domain.HomeworkAssignment;
import com.obelix.homework.platform.model.entity.domain.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.web.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private static final String UPLOAD_DIRECTORY = System.getProperty("") +"/uploads";;
    private final StudentService studentService;

    @GetMapping()
    public String student() {
        return "Hello Student";
    }

    @GetMapping("/assignments")
    public List<HomeworkAssignment> getAssignments() {
        return studentService.getAssignments();
    }

    @GetMapping("/assignments/{id}")
    public HomeworkAssignment getAssignment(@PathVariable UUID id) {
        return studentService.getAssignment(id);
    }

    @PostMapping("/assignments")
    public SubmittedHomeworkAssignment submitAssignment(@RequestBody SubmittedHomeworkAssignmentDto submittedHomeworkAssignmentDto) {
        return studentService.submitAssignment(submittedHomeworkAssignmentDto);
    }

    @PostMapping("/assignments/bulk")
    public List<SubmittedHomeworkAssignment> submitBulkAssignments(@RequestBody List<SubmittedHomeworkAssignmentDto> submittedHomeworkAssignmentDtos) {
        return studentService.submitBulkAssignments(submittedHomeworkAssignmentDtos);
    }

    @GetMapping("/submitted-assignments")
    public List<SubmittedHomeworkAssignment> getSubmittedAssignments() {
        return studentService.getSubmittedAssignments();
    }

    @GetMapping("/submitted-assignments/{id}")
    public SubmittedHomeworkAssignment getSubmittedAssignment(@PathVariable UUID id) {
        return studentService.getSubmittedAssignment(id);
    }

    @GetMapping("/grades")
    public List<Grade> getGrades() {
        return studentService.getGrades();
    }

    @GetMapping("/uploadimage") public String displayUploadForm() {
        return "imageupload/index";
    }

    @PostMapping("/upload") public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException, IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        return "imageupload/index";
    }
}
