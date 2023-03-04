package com.groupeisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.groupeisi.entities.MyUser;

public interface IMyUserRepository extends JpaRepository<MyUser, Integer> {
    MyUser findByUsernameIgnoreCase(String username);
    MyUser findByEmailIgnoreCase(String email);
}
