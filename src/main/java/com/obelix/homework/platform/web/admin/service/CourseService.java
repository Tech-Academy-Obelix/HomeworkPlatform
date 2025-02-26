package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.config.exception.CourseNotFoundException;
import com.obelix.homework.platform.config.exception.SubjectNotFoundException;
import com.obelix.homework.platform.config.exception.UserNotFoundException;
import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectInCourseDto;
import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.Subject;
import com.obelix.homework.platform.model.user.dto.UserDto;
import com.obelix.homework.platform.model.user.entity.Teacher;
import com.obelix.homework.platform.repo.domain.CourseRepo;
import com.obelix.homework.platform.repo.domain.SubjectRepo;
import com.obelix.homework.platform.repo.user.StudentRepo;
import com.obelix.homework.platform.repo.user.TeacherRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {
    private final CourseRepo courseRepo;
    private final TeacherRepo teacherRepo;
    private final StudentRepo studentRepo;
    private final SubjectRepo subjectRepo;

    private final ModelMapper modelMapper;

    public List<CourseDto> getAllCourses() {
        return courseRepo.findAll().stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }

    public CourseDto getCourseById(UUID courseId) {
        return modelMapper.map(getCourseFromRepoById(courseId), CourseDto.class);
    }

    public CourseDto createCourse(String courseName) {
        return modelMapper.map(courseRepo.save(new Course(courseName)), CourseDto.class);
    }

    public List<CourseDto> createCourses(List<String> courseNames) {
        return courseRepo.saveAll(courseNames.stream()
                        .map(Course::new)
                        .collect(Collectors.toList()))
                .stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }

    public void deleteCourseById(UUID courseId) {
        courseRepo.deleteById(courseId);
    }

    public CourseDto addSubjectToCourse(UUID courseId, UUID subjectId) {
        var course = getCourseFromRepoById(courseId);
        course.getSubjects().add(getSubjectFromRepoById(subjectId));
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    public CourseDto addSubjectsToCourse(UUID courseId, List<UUID> subjectIds) {
        return subjectIds.stream()
                .map(subjectId -> addSubjectToCourse(courseId, subjectId))
                .reduce((_, second) -> second)
                .orElseThrow(NullPointerException::new);
    }

    public List<SubjectInCourseDto> getSubjectsInCourse(UUID courseId) {
        return getCourseFromRepoById(courseId).getSubjects().stream()
                .map(subject -> modelMapper.map(subject, SubjectInCourseDto.class))
                .collect(Collectors.toList());
    }

    public CourseDto removeSubjectFromCourse(UUID courseId, UUID subjectId) {
        var course = getCourseFromRepoById(courseId);
        course.removeSubjectById(subjectId);
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    public CourseDto addTeacherToSubjectInCourse(UUID courseId, UUID subjectId, UUID teacherId) {
        var course = getCourseFromRepoById(courseId);
        course.addTeacherToSubject(getTeacherFromRepoById(teacherId), subjectId);
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    public List<UserDto> getStudentsInCourse(UUID courseId) {
        return getCourseById(courseId).getStudents();
    }

    public CourseDto addStudentToCourse(UUID courseId, UUID studentId) {
        var course = getCourseFromRepoById(courseId);
        course.addStudent(studentRepo.findById(studentId).orElseThrow(() -> new UserNotFoundException(studentId.toString())));
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    public CourseDto addStudentsToCourse(UUID courseId, List<UUID> studentIds) {
        return studentIds.stream()
                .map(studentId -> addStudentToCourse(courseId, studentId))
                .reduce((_, second) -> second)
                .orElseThrow(NullPointerException::new);
    }

    public CourseDto removeStudentFromCourse(UUID courseId, UUID studentId) {
        var course = getCourseFromRepoById(courseId);
        course.removeStudentById(studentId);
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    public CourseDto removeTeacherFromSubjectInCourse(UUID courseId, UUID subjectId, UUID teacherId) {
        var course = getCourseFromRepoById(courseId);
        course.removeTeacherFromSubject(teacherId, subjectId);
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    private Teacher getTeacherFromRepoById(UUID teacherId) {
        return teacherRepo.findById(teacherId)
                .orElseThrow(() -> new UserNotFoundException(teacherId.toString()));
    }

    private Course getCourseFromRepoById(UUID courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId.toString()));
    }

    private Subject getSubjectFromRepoById(UUID subjectId) {
        return subjectRepo.findById(subjectId).orElseThrow(() -> new SubjectNotFoundException(subjectId.toString()));
    }

}
