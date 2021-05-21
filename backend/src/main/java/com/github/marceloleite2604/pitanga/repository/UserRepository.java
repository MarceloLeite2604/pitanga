package com.github.marceloleite2604.pitanga.repository;

import com.github.marceloleite2604.pitanga.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
