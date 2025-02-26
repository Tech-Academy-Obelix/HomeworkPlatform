package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.config.exception.AssignmentNotFoundException;
import com.obelix.homework.platform.model.domain.dto.GradeDto;
import com.obelix.homework.platform.model.domain.dto.HomeworkAssignmentCreateDto;
import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.domain.dto.HomeworkAssignmentResponseDto;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectDto;
import com.obelix.homework.platform.model.domain.entity.*;
import com.obelix.homework.platform.model.user.entity.Teacher;
import com.obelix.homework.platform.repo.domain.*;
import com.obelix.homework.platform.repo.user.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherService {
    private final ModelMapper modelMapper;
    private final SubmittedHomeworkAssignmentRepo submittedHomeworkAssignmentRepo;
    private final HomeworkAssignmentRepo homeworkAssignmentRepo;
    private final GradeRepo gradeRepo;
    private final UserService userService;
    private final UserRepo userRepo;

    public List<CourseDto> getCourses() {
        return teacher().getCourses().stream()
                .map(c -> modelMapper.map(c, CourseDto.class))
                .collect(Collectors.toList());
    }

    public CourseDto getCourseById(UUID id) {
        return modelMapper.map(teacher().getCourseById(id), CourseDto.class);
    }

    public List<SubjectDto> getAllSubjectsInCourse(UUID id) {
        var teacher = teacher();
        return teacher.getCourseById(id).getSubjectTeachers().entrySet().stream()
                .filter(entry -> entry.getValue().equals(teacher))
                .map(entry -> modelMapper.map(entry.getKey(), SubjectDto.class))
                .collect(Collectors.toList());
    }

    public HomeworkAssignmentResponseDto createAssignment(HomeworkAssignmentCreateDto dto) {
        var teacher = teacher();
        var assignment = modelMapper.map(dto, HomeworkAssignment.class);
        teacher.addAssignment(homeworkAssignmentRepo.save(assignment));
        userRepo.save(teacher);
        return modelMapper.map(assignment, HomeworkAssignmentResponseDto.class);
    }

    public List<HomeworkAssignmentResponseDto> getAssignments() {
        return teacher().getAssignments().stream()
                .map(assignmentDto -> modelMapper.map(assignmentDto, HomeworkAssignmentResponseDto.class))
                .collect(Collectors.toList());
    }

    public HomeworkAssignmentResponseDto getAssignmentById(UUID id) {
        return modelMapper.map(teacher().getAssignmentById(id), HomeworkAssignmentResponseDto.class);
    }

    public void deleteAssignmentById(UUID id) {
        var teacher = teacher();
        teacher.removeAssignmentById(id);
        userRepo.save(teacher);
    }

    public SubjectDto assignAssignmentToSubjectInCourse(UUID courseId, UUID subjectId, UUID assignmentId) {
        var teacher = teacher();
        var course = teacher.getCourseById(courseId);
        course.assignAssignmentToSubjectById(subjectId, teacher.getAssignmentById(assignmentId));
        userRepo.save(teacher);
        return modelMapper.map(course.getSubjectById(subjectId), SubjectDto.class);
    }

    public List<SubmittedHomeworkAssignment> getSubmittedAssignmentsInCourseBySubjectId(UUID courseId, UUID subjectId) {
            return teacher().getCourseById(courseId).getSubmittedHomeworkAssignmentsBySubjectId(subjectId);
        }

    public SubmittedHomeworkAssignment getSubmittedAssignmentInCourseInSubjectById(UUID courseId, UUID subjectId, UUID assignmentId) {
        return getSubmittedAssignmentsInCourseBySubjectId(courseId, subjectId).stream()
                .filter(assignment -> assignment.getId().equals(assignmentId))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId.toString()));
    }

    public SubmittedHomeworkAssignment gradeSubmittedAssignment(UUID courseId, UUID subjectId, UUID assignmentId, GradeDto gradeDto) {
        var assignment = getSubmittedAssignmentInCourseInSubjectById(courseId, subjectId, assignmentId);
        assignment.setGrade(gradeRepo.save(buildGrade(assignment)));
        assignment.setTeacherComment(gradeDto.getTeacherComment());
        return submittedHomeworkAssignmentRepo.save(assignment);
    }

    private Grade buildGrade(SubmittedHomeworkAssignment assignment){
        return Grade.builder()
                .grade(0)
                .timestamp(new Date())
                .student(assignment.getStudent())
                .teacher(teacher())
                .subject(assignment.getSubject())
                .build();
    }

    private Teacher teacher() {
        return userService.getLoggedInTeacher();
    }
}
