package com.groupeisi.dao;

import com.groupeisi.entities.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModuleRepository extends JpaRepository<ModuleEntity, Integer> {
    ModuleEntity findByName(String name);
}
