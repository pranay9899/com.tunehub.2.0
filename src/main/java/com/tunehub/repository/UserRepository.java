package com.tunehub.repository;

import com.tunehub.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByName(String name);
    Users findByEmail(String email);

    boolean existsByEmail(String email);

}
