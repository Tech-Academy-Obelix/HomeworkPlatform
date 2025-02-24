package com.obelix.homework.platform.web.admin.controller;

import com.obelix.homework.platform.model.domain.dto.SubjectDto;
import com.obelix.homework.platform.web.admin.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectDto> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public SubjectDto getSubjectById(@PathVariable UUID id) {
        return subjectService.getSubjectById(id);
    }

    @PostMapping
    public SubjectDto createSubject(@RequestBody String subjectName) {
        return subjectService.createSubject(subjectName);
    }

    @DeleteMapping("/{id}")
    public void deleteSubjectById(@PathVariable UUID id) {
        subjectService.deleteSubjectById(id);
    }
}
