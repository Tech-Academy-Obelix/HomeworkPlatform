package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.config.exception.AssignmentNotFoundException;
import com.obelix.homework.platform.config.exception.CourseNotFoundException;
import com.obelix.homework.platform.model.dto.domain.GradeDto;
import com.obelix.homework.platform.model.dto.domain.HomeworkAssingmentDto;
import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.Grade;
import com.obelix.homework.platform.model.entity.domain.HomeworkAssignment;
import com.obelix.homework.platform.model.entity.domain.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.model.entity.user.Teacher;
import com.obelix.homework.platform.repo.domain.CourseRepo;
import com.obelix.homework.platform.repo.domain.GradeRepo;
import com.obelix.homework.platform.repo.domain.HomeworkAssignmentRepo;
import com.obelix.homework.platform.repo.domain.SubmittedHomeworkAssignmentRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final HomeworkAssignmentRepo homeworkAssignmentRepo;
    private final ModelMapper modelMapper;
    private final CourseRepo courseRepo;
    private final SubmittedHomeworkAssignmentRepo submittedHomeworkAssignmentRepo;
    private final GradeRepo gradeRepo;
    private final UserService userService;
    private Teacher teacher;

    public HomeworkAssignment createAssignment(HomeworkAssingmentDto dto) {
        return homeworkAssignmentRepo.save(modelMapper.map(dto, HomeworkAssignment.class));
    }

    public List<HomeworkAssignment> getHomeworkAssignments() {
        return teacher.getCourses().stream()
                .flatMap(course -> course.getAssignments().stream())
                .collect(Collectors.toList());
    }

    public HomeworkAssignment getAssignment(UUID id) {
        return teacher.getCourses().stream()
                .flatMap(course -> course.getAssignments().stream())
                .filter(assignment -> assignment.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(id.toString()));
    }

    public List<HomeworkAssignment> assignAssignmentToCourse(UUID id, UUID courseId) {
        var course = getCourseById(courseId);
        course.getAssignments().add(getAssignment(id));
        return courseRepo.save(course).getAssignments();
    }

    public List<SubmittedHomeworkAssignment> getSubmittedHomeworkAssignments() {
            return teacher.getCourses().stream()
                    .flatMap(course -> course.getStudents().stream()
                            .flatMap(student -> student.getSubmittedHomeworkAssignments().stream()))
                    .collect(Collectors.toList());
        }

    public SubmittedHomeworkAssignment getSubmittedAssignmentById(UUID id) {
        return teacher.getCourses().stream()
                .flatMap(course -> course.getStudents().stream()
                        .flatMap(student -> student.getSubmittedHomeworkAssignments().stream()))
                .filter(assignment -> assignment.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(id.toString()));
    }

    public SubmittedHomeworkAssignment gradeSubmittedAssignment(UUID id, GradeDto gradeDto) {
        var assignment = getSubmittedAssignmentById(id);
        assignment.setGrade(gradeRepo.save(buildGrade(assignment)));
        assignment.setTeacherComment(gradeDto.getTeacherComment());
        return submittedHomeworkAssignmentRepo.save(assignment);
    }

    public List<Course> getCourses() {
        return teacher.getCourses();
    }

    public Course getCourseById(UUID id) {
        return teacher.getCourses().stream()
                .filter(course -> course.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(id.toString()));

    }

    private Grade buildGrade(SubmittedHomeworkAssignment assignment){
        return Grade.builder()
                .grade(0)
                .timestamp(new Date())
                .student(assignment.getStudent())
                .teacher(teacher)
                .subject(assignment.getSubject())
                .build();
    }

    @PostConstruct
    public void init() {
        teacher = (Teacher) userService.getLoggedInUser();
    }
}
