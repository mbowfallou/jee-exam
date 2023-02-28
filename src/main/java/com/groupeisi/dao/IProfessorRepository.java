package com.groupeisi.dao;

import com.groupeisi.entities.ProfessorEntity;
import com.groupeisi.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProfessorRepository extends JpaRepository<ProfessorEntity, Integer> {
    ProfessorEntity findByEmail(String email);
    List<ProfessorEntity> findByNom(String nom);
    List<ProfessorEntity> findByPrenom(String prenom);
    List<ProfessorEntity> findByPrenomAndNom(String prenom, String nom);
    ProfessorEntity findByEmailAndPassword(String email, String password);
}
