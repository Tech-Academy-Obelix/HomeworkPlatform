package com.obelix.homework.platform.web.service;

import com.obelix.homework.platform.config.exception.CourseNotFoundException;
import com.obelix.homework.platform.model.dto.CourseDto;
import com.obelix.homework.platform.model.dto.GradeDto;
import com.obelix.homework.platform.model.dto.HomeworkAssingmentDto;
import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.Grade;
import com.obelix.homework.platform.model.entity.domain.HomeworkAssignment;
import com.obelix.homework.platform.model.entity.domain.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.model.entity.user.Teacher;
import com.obelix.homework.platform.repo.CourseRepo;
import com.obelix.homework.platform.repo.GradeRepo;
import com.obelix.homework.platform.repo.HomeworkAssignmentRepo;
import com.obelix.homework.platform.repo.SubmittedHomeworkAssignmentRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public SubmittedHomeworkAssignment gradeSubmittedAssignments(UUID id, GradeDto gradeDto) {
        var assignment = submittedHomeworkAssignmentRepo.getSubmittedHomeworkAssignmentById(id);
        var grade = buildGrade(assignment);
        assignment.setGrade(grade);
        assignment.setTeacherComment(gradeDto.getTeacherComment());
        gradeRepo.save(grade);
        return submittedHomeworkAssignmentRepo.save(assignment);
    }

    public Grade buildGrade(SubmittedHomeworkAssignment assignment){
        return Grade.builder()
               .grade(0)
               .timestamp(new Date())
               .student(assignment.getStudent())
               .teacher(teacher)
               .subject(assignment.getSubject())
               .build();
    }

    public Course getCourse() {
        return teacher.getOwnCourse();
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

    @PostConstruct
    public void init() {
        teacher = (Teacher) userService.getLoggedInUser();
    }
}
