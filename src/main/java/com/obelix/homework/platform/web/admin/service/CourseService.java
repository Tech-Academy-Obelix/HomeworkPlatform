package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.config.exception.CourseNotFoundException;
import com.obelix.homework.platform.config.exception.SubjectNotFoundException;
import com.obelix.homework.platform.config.exception.UserNotFoundException;
import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.domain.dto.SubjectDto;
import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.Subject;
import com.obelix.homework.platform.repo.domain.CourseRepo;
import com.obelix.homework.platform.repo.domain.CourseSubjectRepo;
import com.obelix.homework.platform.repo.domain.SubjectRepo;
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
    private final SubjectRepo subjectRepo;
    private final TeacherRepo teacherRepo;
    private final CourseSubjectRepo courseSubjectRepo;
    private final ModelMapper modelMapper;

    public List<CourseDto> getAllCourseDtos() {
        return courseRepo.findAll().stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }

    public CourseDto getCourseDtoById(UUID courseId) {
        return modelMapper.map(getCourseById(courseId), CourseDto.class);
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
        var course = getCourseById(courseId);
        course.getSubjects().add(getSubjectById(subjectId));
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }
    public CourseDto addSubjectsToCourse(UUID courseId, List<UUID> subjectIds) {
        var course = getCourseById(courseId);
        course.getSubjects().addAll(subjectIds.stream()
                .map(this::getSubjectById)
                .toList());
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }


    public List<SubjectDto> getSubjectsInCourse(UUID courseId) {
        return courseSubjectRepo.getCourseSubjectsById(courseId).stream()
                .map(subject -> modelMapper.map(subject, SubjectDto.class))
                .collect(Collectors.toList());
    }

    public CourseDto removeSubjectFromCourse(UUID courseId, UUID subjectId) {
        var course = getCourseById(courseId);
        course.getSubjects().remove(getSubjectById(subjectId));
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    public CourseDto addTeacherToSubjectInCourse(UUID courseId, UUID subjectId, UUID teacherId) {
        var courseSubject = courseSubjectRepo.findCourseSubjectsById(courseId).stream()
                .filter(courseSubject1 -> courseSubject1.getSubject().getId().equals(subjectId))
                .findFirst()
                .orElseThrow(() -> new SubjectNotFoundException(subjectId.toString()));
        courseSubject.setTeacher(teacherRepo.findById(teacherId).orElseThrow(() -> new UserNotFoundException(teacherId.toString())));
        return modelMapper.map(courseSubjectRepo.save(courseSubject).getCourse(), CourseDto.class);
    }

    private Subject getSubjectById(UUID subjectId) {
        return subjectRepo.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(subjectId.toString()));
    }

    private Course getCourseById(UUID courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId.toString()));
    }
}
