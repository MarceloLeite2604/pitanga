package com.github.marceloleite2604.pitanga.repository;

import com.github.marceloleite2604.pitanga.model.Vote;
import com.github.marceloleite2604.pitanga.model.attendee.AttendeeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, AttendeeId> {
}
