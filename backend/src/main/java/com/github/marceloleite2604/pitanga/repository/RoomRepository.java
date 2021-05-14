package com.github.marceloleite2604.pitanga.repository;

import com.github.marceloleite2604.pitanga.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
