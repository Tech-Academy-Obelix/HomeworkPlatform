package com.obelix.homework.platform.model.domain.entity;

import com.obelix.homework.platform.model.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Teacher teacher;
}
