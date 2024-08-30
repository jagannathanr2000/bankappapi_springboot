package com.mycompany.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.bankingapp.entity.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long>{
}
