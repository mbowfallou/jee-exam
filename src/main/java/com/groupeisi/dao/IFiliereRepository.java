package com.groupeisi.dao;

import com.groupeisi.entities.FiliereEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFiliereRepository extends JpaRepository<FiliereEntity, Integer> {
    FiliereEntity findByName(String name);
}
