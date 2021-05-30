package com.github.marceloleite2604.pitanga.model.attendee;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.Vote;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Attendee {

    @EmbeddedId
    private AttendeeId id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("roomId")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("userId")
    private User user;

    private String icon;

    private LocalDateTime joinedAt;

    @OneToOne(mappedBy="attendee", cascade = CascadeType.ALL)
    private Vote vote;
}
