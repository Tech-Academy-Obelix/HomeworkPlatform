package com.obelix.homework.platform.web.admin.controller;

import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.web.admin.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

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
}
