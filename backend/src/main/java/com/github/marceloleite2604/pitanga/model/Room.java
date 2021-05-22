package com.github.marceloleite2604.pitanga.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Room {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Singular
    @OneToMany(mappedBy = "room")
    private Set<Attendee> attendees;

    @JsonIgnore
    private LocalDateTime lastUpdate;
}
