package com.groupeisi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
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
    private String password;
    private int etat;
    @Column(nullable = false)
    private Classe classe;
    @ManyToOne
    private FiliereEntity filiere;
    @ManyToOne
    private AppRolesEntity appRoles;
}
