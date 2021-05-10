package com.github.marceloleite2604.pitanga.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Room {

    private final long id;

    @JsonIgnore
    private final LocalDateTime lastUpdate;
}
