package com.groupeisi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor {
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
    private List<String> modules;
}
