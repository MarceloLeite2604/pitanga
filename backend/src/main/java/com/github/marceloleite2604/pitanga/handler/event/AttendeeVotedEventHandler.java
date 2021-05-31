package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.attendeevoted.AttendeeVotedEvent;
import com.github.marceloleite2604.pitanga.model.event.attendeevoted.AttendeeVotedPayload;
import com.github.marceloleite2604.pitanga.model.mapper.AttendeeToDao;
import com.github.marceloleite2604.pitanga.model.mapper.RoomToDao;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AttendeeVotedEventHandler extends AbstractEventHandler<AttendeeVotedPayload> {

    private final UserToDao userToDao;
    private final AttendeeToDao attendeeToDao;
    private final RoomToDao roomToDao;

    public AttendeeVotedEventHandler(PitangaService pitangaService, UserToDao userToDao, AttendeeToDao attendeeToDao, RoomToDao roomToDao) {
        super(pitangaService, EventType.ATTENDEE_VOTED);
        this.userToDao = userToDao;
        this.attendeeToDao = attendeeToDao;
        this.roomToDao = roomToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var incomingAttendeeVotedPayload = retrievePayload(incomingContext);

        var attendeeDao = incomingAttendeeVotedPayload.getAttendee();

        var attendee = attendeeToDao.mapFrom(attendeeDao);

        attendee = pitangaService.updateVoteForAttendee(attendee);

        Set<UserDao> recipients = attendee.getRoom()
                .getAttendees()
                .stream()
                .map(Attendee::getUser)
                .map(userToDao::mapTo)
                .collect(Collectors.toCollection(HashSet::new));

        attendeeDao = attendeeToDao.mapTo(attendee);
        var roomDao = roomToDao.mapTo(attendee.getRoom());

        var outgoingAttendeeVotedPayload = AttendeeVotedPayload.builder()
                .attendee(attendeeDao)
                .votingStatus(roomDao
                        .getVotingStatus())
                .build();

        var attendeeVotedEvent = AttendeeVotedEvent.builder()
                .payload(outgoingAttendeeVotedPayload)
                .build();

        return OutgoingContext.builder()
                .event(attendeeVotedEvent)
                .recipients(recipients)
                .build();
    }
}
