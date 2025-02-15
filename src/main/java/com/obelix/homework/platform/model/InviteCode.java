package com.obelix.homework.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


/***
 * The invite code is what allows user to register in the Homework Platform.
 * Based on the invite code the role for the new user is determined.
 * It is granted by the admin via the admin controller where they specify the email address
 * and the role of the new person they want to allow. The invite code is sent to that email
 * After that the person that received it can register an account with it. The invite code is
 * their first password, which they are then prompted to change.
 */

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String role;
    String associatedEmail;

    @Override
    public String toString() {
        return id.toString();
    }
}
