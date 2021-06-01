package com.github.marceloleite2604.pitanga.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.attendee.AttendeeId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Vote {

    @EmbeddedId
    @ToString.Include
    private AttendeeId id;

    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @MapsId
    private Attendee attendee;

    private int effort;

    private int value;
}
