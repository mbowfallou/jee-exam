package com.groupeisi.dao;

import com.groupeisi.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStudentRepository extends JpaRepository<StudentEntity, Integer> {
    StudentEntity findByEmail(String email);
    StudentEntity findByUsername(String username);
    List<StudentEntity> findByNom(String nom);
    List<StudentEntity> findByPrenom(String prenom);
    List<StudentEntity> findByPrenomAndNom(String prenom, String nom);
    StudentEntity findByEmailAndPassword(String email, String password);
}
