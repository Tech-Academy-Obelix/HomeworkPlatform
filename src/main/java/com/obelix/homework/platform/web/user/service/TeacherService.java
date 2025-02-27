package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.config.exception.AssignmentNotFoundException;
import com.obelix.homework.platform.model.domain.dto.GradeDto;
import com.obelix.homework.platform.model.domain.dto.assignment.HomeworkAssignmentCreateDto;
import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.domain.dto.assignment.HomeworkAssignmentResponseDto;
import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionDto;
import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionTeacherDto;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectDto;
import com.obelix.homework.platform.model.domain.entity.*;
import com.obelix.homework.platform.model.user.dto.StudentDto;
import com.obelix.homework.platform.model.user.entity.Teacher;
import com.obelix.homework.platform.repo.domain.*;
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
    private final GradeRepo gradeRepo;
    private final UserService userService;
    private final CourseRepo courseRepo;
    private final HomeworkAssignmentRepo homeworkAssignmentRepo;

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

    public List<HomeworkAssignmentResponseDto> getAssignments(UUID courseId, UUID subjectId) {
        return teacher().getCourseById(courseId).getAssignmentsInSubject(subjectId).stream()
                .map(assignmentDto -> modelMapper.map(assignmentDto, HomeworkAssignmentResponseDto.class))
                .collect(Collectors.toList());
    }

    public HomeworkAssignmentResponseDto getAssignmentById(UUID courseId, UUID subjectId, UUID assignmentId) {
        return getAssignments(courseId, subjectId).stream()
                .filter(assignment -> assignment.getId().equals(assignmentId))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId.toString()));
    }

    public HomeworkAssignmentResponseDto createAssignment(UUID courseId, UUID subjectId, HomeworkAssignmentCreateDto dto) {
        var course = teacher().getCourseById(courseId);
        var assignment = modelMapper.map(dto, HomeworkAssignment.class);
        homeworkAssignmentRepo.save(assignment);
        course.addAssignmentToSubject(assignment, subjectId);
        courseRepo.save(course);
        return modelMapper.map(assignment, HomeworkAssignmentResponseDto.class);
    }

    public void deleteAssignment(UUID courseId, UUID assignmentId) {
        var course = teacher().getCourseById(courseId);
        course.removeAssignmentById(assignmentId);
        courseRepo.save(course);
        homeworkAssignmentRepo.deleteById(assignmentId);
    }

    public List<SubmissionDto> getSubmittedAssignmentsInCourseBySubjectId(UUID courseId, UUID subjectId) {
        var course = teacher().getCourseById(courseId);
        var submissions = course.getSubmittedHomeworkAssignmentsBySubjectId(subjectId);
        return submissions.stream()
                .map(submission -> getSubmissionDto(course, submission))
                .collect(Collectors.toList());
    }

    public SubmissionDto getSubmittedAssignmentInCourseInSubjectById(UUID courseId, UUID subjectId, UUID assignmentId) {
        return getSubmittedAssignmentsInCourseBySubjectId(courseId, subjectId).stream()
                .filter(submission -> submission.getHomeworkAssignment().getId().equals(assignmentId))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId.toString()));
    }

    public SubmissionDto gradeSubmittedAssignment(UUID courseId, UUID submissionId, GradeDto gradeDto) {
        var course = teacher().getCourseById(courseId);
        var submission = course.getSubmissionById(submissionId);
        submission.setGrade(gradeRepo.save(buildGrade(submission)));
        submission.setTeacherComment(gradeDto.getTeacherComment());
        var dto = getSubmissionDto(course, submission);
        submittedHomeworkAssignmentRepo.save(submission);
        return dto;
    }

    private Grade buildGrade(Submission assignment) {
        return Grade.builder()
                .grade(0)
                .timestamp(new Date())
                .student(assignment.getStudent())
                .teacher(teacher())
                .build();
    }

    private SubmissionDto getSubmissionDto(Course course, Submission submission) {
        var dto = modelMapper.map(submission, SubmissionTeacherDto.class);
        var student = course.getStudentBySubmissionId(submission.getId());
        dto.setStudent(modelMapper.map(student, StudentDto.class));
        return dto;
    }

    private Teacher teacher() {
        return userService.getLoggedInTeacher();
    }
}
