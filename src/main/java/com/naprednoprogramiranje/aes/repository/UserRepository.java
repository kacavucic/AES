package com.naprednoprogramiranje.aes.repository;

import com.naprednoprogramiranje.aes.model.User;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, DataTablesRepository<User, Long> {

    User findByUsername(String username);

    User findByMobile(String mobile);

    User findByEmail(String email);

}
