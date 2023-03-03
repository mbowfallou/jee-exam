package com.groupeisi.dto;

import com.groupeisi.entities.Classe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    @NotNull(message = "Le nom ne doit pas etre nul!")
    private String nom;
    @NotNull(message = "Le prenom ne doit pas etre nul!")
    private String prenom;
    private String adresse;
    @NotNull(message = "L'email ne doit pas etre nul!")
    private String email;
    private String password;
    private int etat;
    private Classe classe;
    private int filiere_id;
    private String filiere_name;
    private String roles_name;
    private String appRoles;
}
