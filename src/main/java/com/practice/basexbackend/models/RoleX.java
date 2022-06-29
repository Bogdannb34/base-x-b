package com.practice.basexbackend.models;

import com.practice.basexbackend.enumeration.ERole;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole roleName;
}
