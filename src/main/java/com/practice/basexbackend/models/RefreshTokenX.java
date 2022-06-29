package com.practice.basexbackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter @Setter @RequiredArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshTokenX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userX_id", referencedColumnName = "id")
    private UserX user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
