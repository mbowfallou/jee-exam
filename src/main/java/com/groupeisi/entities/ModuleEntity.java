package com.groupeisi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "modules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 200, nullable = false)
    private String name;
    private String jours;
    private int credit;
    private int quantum;
    private String docs; //private MultipartFile[] docs;
    @ManyToOne(fetch = FetchType.LAZY) //@JoinColumn(name = "professor_id", referencedColumnName = "id")
    private ProfessorEntity professor;
    @ManyToOne(fetch = FetchType.LAZY)
    private FiliereEntity filiere;
}
