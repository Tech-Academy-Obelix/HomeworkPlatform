package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.config.exception.CourseNotFoundException;
import com.obelix.homework.platform.config.exception.SubjectNotFoundException;
import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.repo.domain.CourseRepo;
import com.obelix.homework.platform.repo.domain.CourseSubjectRepo;
import com.obelix.homework.platform.repo.domain.SubjectRepo;
import com.obelix.homework.platform.repo.user.TeacherRepo;
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

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public Course getCourseById(UUID courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId.toString()));
    }

    public Course createCourse(String courseName) {
        return courseRepo.save(new Course(courseName));
    }

    public List<Course> createCourses(List<String> courseNames) {
        return courseRepo.saveAll(courseNames.stream()
                .map(Course::new)
                .collect(Collectors.toList()));
    }

    public void deleteCourseById(UUID courseId) {
        courseRepo.deleteById(courseId);
    }

    public Course addSubjectToCourse(UUID courseId, UUID subjectId) {
        var course = getCourseById(courseId);
        course.getSubjects().add(getSubjectById(subjectId));
        return courseRepo.save(course);
    }
    public Course addSubjectsToCourse(UUID courseId, List<UUID> subjectIds) {
        var course = getCourseById(courseId);
        course.getSubjects().addAll(subjectIds.stream()
                .map(this::getSubjectById)
                .toList());
        return courseRepo.save(course);
    }


    public List<Subject> getSubjectsInCourse(UUID courseId) {
        return courseSubjectRepo.getCourseSubjectsById(courseId);
    }

    public Course removeSubjectFromCourse(UUID courseId, UUID subjectId) {
        var course = getCourseById(courseId);
        course.getSubjects().remove(getSubjectById(subjectId));
        return courseRepo.save(course);
    }

    public Course addTeacherToSubjectInCourse(UUID courseId, UUID subjectId, UUID teacherId) {
        var courseSubject = courseSubjectRepo.findCourseSubjectsById(courseId).stream()
                .filter(courseSubject1 -> courseSubject1.getSubject().getId().equals(subjectId))
                .findFirst()
                .orElseThrow(() -> new SubjectNotFoundException(subjectId.toString()));
        courseSubject.setTeacher(teacherRepo.getTeacherById(teacherId));
        return courseSubjectRepo.save(courseSubject).getCourse();
    }

    private Subject getSubjectById(UUID subjectId) {
        return subjectRepo.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(subjectId.toString()));
    }
}
