package com.groupeisi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser2Entity {
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
//    @ManyToMany
//    private List<AppRolesEntity> appRolesEntities;
}
