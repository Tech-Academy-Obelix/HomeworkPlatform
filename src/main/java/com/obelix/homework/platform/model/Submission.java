package com.obelix.homework.platform.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String studentUsername;  // Потребителско име (ученик)
    private String assignmentTitle;  // Име на задачата

    @Lob
    private String content;  // Допълнителна информация към домашното
    private String status;  // status

    private String filePath;  // file path
}
