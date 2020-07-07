package com.github.rcmarc.appvpn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "token")
public class VerificationToken {
    @javax.persistence.Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;

    private String token;

    @OneToOne(fetch = LAZY)
    private User user;

}

