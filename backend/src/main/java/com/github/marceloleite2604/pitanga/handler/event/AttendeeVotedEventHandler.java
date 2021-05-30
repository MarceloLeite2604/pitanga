package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.Vote;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.attendeevoted.AttendeeVotedEvent;
import com.github.marceloleite2604.pitanga.model.event.attendeevoted.AttendeeVotedPayload;
import com.github.marceloleite2604.pitanga.model.mapper.AttendeeToDao;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AttendeeVotedEventHandler extends AbstractEventHandler<AttendeeVotedPayload> {

    private final UserToDao userToDao;
    private final AttendeeToDao attendeeToDao;

    public AttendeeVotedEventHandler(PitangaService pitangaService, UserToDao userToDao, AttendeeToDao attendeeToDao) {
        super(pitangaService, EventType.ATTENDEE_VOTED);
        this.userToDao = userToDao;
        this.attendeeToDao = attendeeToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var attendeeVotedPayload = retrievePayload(incomingContext);

        var attendeeDao = attendeeVotedPayload.getAttendee();

        var userId = UUID.fromString(attendeeDao.getUser()
                .getId());

        var optionalPersistedAttendee = pitangaService.findAttendeeByUserId(userId);


        Set<UserDao> recipients = null;

        if (optionalPersistedAttendee.isPresent()) {

            var attendee = attendeeToDao.mapFrom(attendeeDao);
            var persistedAttendee = optionalPersistedAttendee.get();

            Vote vote;
            if (Objects.nonNull(persistedAttendee.getVote())) {
                vote = persistedAttendee.getVote();
                vote.setEffort(attendee.getVote().getEffort());
                vote.setValue(attendee.getVote().getValue());
            } else {
                vote = attendee.getVote();
                vote.setAttendee(persistedAttendee);
            }

            pitangaService.persistVote(vote);

            recipients = optionalPersistedAttendee.get()
                    .getRoom()
                    .getAttendees()
                    .stream()
                    .map(Attendee::getUser)
                    .map(userToDao::mapTo)
                    .collect(Collectors.toCollection(HashSet::new));
        }

        var attendeeVotedEvent = AttendeeVotedEvent.builder()
                .payload(attendeeVotedPayload)
                .build();

        return OutgoingContext.builder()
                .event(attendeeVotedEvent)
                .recipients(recipients)
                .build();
    }
}
