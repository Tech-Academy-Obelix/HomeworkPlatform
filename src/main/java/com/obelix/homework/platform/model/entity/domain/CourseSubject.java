package com.obelix.homework.platform.model.entity.domain;

import com.obelix.homework.platform.model.entity.user.Teacher;
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
