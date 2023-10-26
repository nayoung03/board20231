package com.example.board20231.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUsername(Long aLong);

    Optional<SiteUser> findByusername(String username);
}


