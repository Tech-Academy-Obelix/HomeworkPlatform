package com.obelix.homework.platform.web.service;

import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.CourseSubject;
import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.model.entity.user.Teacher;
import com.obelix.homework.platform.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;
    private final SubjectRepo subjectRepo;
    private final TeacherRepo teacherRepo;
    private final CourseSubjectRepo courseSubjectRepo;

    public void assignTeacherToSubjectInCourse(UUID courseId, UUID subjectId, UUID teacherId) {
        courseSubjectRepo.save(CourseSubject.builder()
                .course(courseRepo.getCoursesById(courseId))
                .subject(subjectRepo.getSubjectById(subjectId))
                .teacher(teacherRepo.getTeacherById(teacherId))
                .build());
    }

    // Get all subjects for a course
    public List<Subject> getSubjectsForCourse(UUID courseId) {
        return courseRepo.findById(courseId)
                .map(course -> {
                    List<CourseSubject> courseSubjects = courseSubjectRepo.findCourseSubjectByCourse(course);
                    return courseSubjects.stream()
                            .map(CourseSubject::getSubject)
                            .collect(Collectors.toList());
                })
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
}
