package com.github.marceloleite2604.pitanga.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    private UUID id;

    @JsonIgnore
    private String sessionId;

    @JsonIgnore
    @ManyToOne
    private Room room;
}
