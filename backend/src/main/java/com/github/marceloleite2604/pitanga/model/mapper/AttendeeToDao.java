package com.github.marceloleite2604.pitanga.model.mapper;

import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.attendee.AttendeeId;
import com.github.marceloleite2604.pitanga.model.dao.AttendeeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class AttendeeToDao implements Mapper<Attendee, AttendeeDao> {

    private final UserToDao userToDao;

    private final VoteToDao voteToDao;

    @Override
    public AttendeeDao mapTo(Attendee attendee) {
        return AttendeeDao.builder()
                .user(userToDao.mapTo(attendee.getUser()))
                .vote(voteToDao.mapTo(attendee.getVote()))
                .icon(attendee.getIcon())
                .joinedAt(attendee.getJoinedAt()
                        .toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    @Override
    public Attendee mapFrom(AttendeeDao attendeeDao) {

        var user = userToDao.mapFrom(attendeeDao.getUser());

        var vote = voteToDao.mapFrom(attendeeDao.getVote());

        var id = AttendeeId.builder()
                .userId(user.getId())
                .build();

        return Attendee.builder()
                .id(id)
                .icon(attendeeDao.getIcon())
                .user(user)
                .vote(vote)
                .build();
    }
}
