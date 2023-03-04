package com.groupeisi.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppRolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 150, nullable = false, unique = true)
    private String nom;
//    @OneToMany(mappedBy = "appRoles")
//    private List<StudentEntity> studentEntities;
//    @OneToMany(mappedBy = "appRoles")
//    private List<ProfessorEntity> professorEntities;
}
