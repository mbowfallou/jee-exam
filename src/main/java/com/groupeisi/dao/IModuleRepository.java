package com.groupeisi.dao;

import com.groupeisi.entities.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IModuleRepository extends JpaRepository<ModuleEntity, Integer> {
    List<ModuleEntity> findByName(String name);
}
