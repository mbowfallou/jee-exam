package com.groupeisi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "professors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50, nullable = false)
    private String nom;
    @Column(length = 80, nullable = false)
    private String prenom;
    @Column(length = 200)
    private String adresse;
    @Column(length = 150, nullable = false, unique = true)
    private String email;
    @Column(length = 150, nullable = false, unique = true)
    private String username;
    private String password;
    private int etat;
    @ManyToOne
    private AppRolesEntity appRoles;
}
