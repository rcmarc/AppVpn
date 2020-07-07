package com.github.rcmarc.appvpn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "username cannot be empty")
    @NotNull(message = "username cannot be null")
    private String username;

    @NotBlank(message = "password cannot be empty")
    @NotNull(message = "password cannot be null")
    private String password;

    @Email
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    private String email;

    private LocalDateTime createdDate;

    private boolean enabled;
}
