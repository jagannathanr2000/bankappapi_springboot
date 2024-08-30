package com.mycompany.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.authservice.entity.User;

@Repository
public interface AuthRepository extends JpaRepository<User,String>{
}
