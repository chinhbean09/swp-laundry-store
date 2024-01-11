package com.project.SWP391.repositories;


import com.project.SWP391.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}
