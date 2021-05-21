package com.github.marceloleite2604.pitanga.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    private UUID id;

//    @JsonIgnore
//    @Setter
//    private String sessionId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Room room;
}
