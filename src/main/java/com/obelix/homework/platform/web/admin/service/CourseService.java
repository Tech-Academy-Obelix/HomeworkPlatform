package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.config.exception.CourseNotFoundException;
import com.obelix.homework.platform.config.exception.SubjectHasAssignedTeacherException;
import com.obelix.homework.platform.config.exception.SubjectNotFoundException;
import com.obelix.homework.platform.config.exception.UserNotFoundException;
import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.domain.dto.SubjectDto;
import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.Subject;
import com.obelix.homework.platform.model.user.entity.Teacher;
import com.obelix.homework.platform.repo.domain.CourseRepo;
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
    private final TeacherRepo teacherRepo;
    private final SubjectRepo subjectRepo;

    private final ModelMapper modelMapper;

    public List<CourseDto> getAllCourseDtos() {
        return courseRepo.findAll().stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }

    public CourseDto getCourseDtoById(UUID courseId) {
        return modelMapper.map(getCourseByRepoById(courseId), CourseDto.class);
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
        var course = getCourseByRepoById(courseId);
        course.getSubjects().add(getSubjectFromRepoById(subjectId));
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    public CourseDto addSubjectsToCourse(UUID courseId, List<UUID> subjectIds) {
        var course = getCourseByRepoById(courseId);
        course.getSubjects().addAll(subjectIds.stream()
                .map(this::getSubjectFromRepoById).toList());
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    public List<SubjectDto> getSubjectsInCourse(UUID courseId) {
        return getCourseByRepoById(courseId).getSubjects().stream()
                .map(subject -> modelMapper.map(subject, SubjectDto.class))
                .collect(Collectors.toList());
    }

    public CourseDto removeSubjectFromCourse(UUID courseId, UUID subjectId) {
        var course = getCourseByRepoById(courseId);
        course.removeSubjectById(subjectId);
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    public CourseDto addTeacherToSubjectInCourse(UUID courseId, UUID subjectId, UUID teacherId) {
        var course = getCourseByRepoById(courseId);
        var subject = course.getSubjectById(subjectId);
        var teacher = getTeacherByRepoById(teacherId);
        throwIfTeacherAssigned(course, subject);
        saveTeacherInSubjectIfNotExist(subject, teacher);
        course.getSubjectTeachers().put(subject, teacher);
        return modelMapper.map(courseRepo.save(course), CourseDto.class);

    }

    private void throwIfTeacherAssigned(Course course, Subject subject) {
        if (course.getSubjectTeachers().containsKey(subject)) {
            throw new SubjectHasAssignedTeacherException(subject.getId().toString());
        }
    }

    public CourseDto removeTeacherFromSubjectInCourse(UUID courseId, UUID subjectId, UUID teacherId) {
        var course = getCourseByRepoById(courseId);
        var teacher = getTeacherByRepoById(teacherId);
        var subject = course.getSubjectById(subjectId);
        course.getSubjectTeachers().remove(subject);
        deleteTeacherInSubject(course, subject, teacher);
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    private void deleteTeacherInSubject(Course course, Subject subject, Teacher teacher) {
        var courseTeachers = course.getSubjectTeachers();
        courseTeachers.remove(subject);
        subject.unassignTeacher();
        if (subject.getAssignedTeachers() == 0) {
            subject.getTeachers().remove(teacher);
            subjectRepo.save(subject);
        }
    }

    private void saveTeacherInSubjectIfNotExist(Subject subject, Teacher teacher) {
        if (!subject.getTeachers().contains(teacher)) {
            subject.addTeacher(teacher);
            subjectRepo.save(subject);
        }
    }

    private Teacher getTeacherByRepoById(UUID teacherId) {
        return teacherRepo.findById(teacherId)
                .orElseThrow(() -> new UserNotFoundException(teacherId.toString()));
    }

    private Course getCourseByRepoById(UUID courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId.toString()));
    }

    private Subject getSubjectFromRepoById(UUID subjectId) {
        return subjectRepo.findById(subjectId).orElseThrow(() -> new SubjectNotFoundException(subjectId.toString()));
    }
}
