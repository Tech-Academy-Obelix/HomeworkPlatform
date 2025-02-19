package com.obelix.homework.platform.web.service;

import com.obelix.homework.platform.model.dto.CourseDto;
import com.obelix.homework.platform.model.dto.HomeworkAssingmentDto;
import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.HomeworkAssignment;
import com.obelix.homework.platform.model.entity.domain.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.repo.CourseRepo;
import com.obelix.homework.platform.repo.HomeworkAssignmentRepo;
import com.obelix.homework.platform.repo.SubmittedHomeworkAssignmentRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class TeacherService {
    private final HomeworkAssignmentRepo homeworkAssignmentRepo;
    private final ModelMapper modelMapper;
    private final CourseRepo courseRepo;
    private final SubmittedHomeworkAssignmentRepo submittedHomeworkAssignmentRepo;


    public HomeworkAssignment createAssignment(HomeworkAssingmentDto dto) {
        return homeworkAssignmentRepo.save(modelMapper.map(dto, HomeworkAssignment.class));
    }

    public List<HomeworkAssignment> getHomeworkAssignments() {
        return homeworkAssignmentRepo.findAll();
    }

    public HomeworkAssignment getAssignment(UUID id) {
        return homeworkAssignmentRepo.getHomeworkAssignmentById(id);
    }


    public List<HomeworkAssignment> assignAssignmentToCourse(UUID id, CourseDto courseDto) {
        Course course = courseRepo.getCourseByCourseName(courseDto.getCourseName());
        course.getAssignments().add(getAssignment(id));
        courseRepo.save(course);
        return course.getAssignments();
    }

    public List<SubmittedHomeworkAssignment> getSubmittedHomeworkAssignments() {
        return submittedHomeworkAssignmentRepo.findAll();
    }

    public SubmittedHomeworkAssignment getSubmittedAssignment(UUID id) {
        return submittedHomeworkAssignmentRepo.getSubmittedHomeworkAssignmentById(id);
    }
}
