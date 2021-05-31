package com.github.marceloleite2604.pitanga.repository;

import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.attendee.AttendeeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AttendeeRepository extends JpaRepository<Attendee, AttendeeId> {

    Optional<Attendee> findByUserId(UUID userId);

    Set<Attendee> findAllByRoomId(Long id);
}
