package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.attendeevoted.AttendeeVotedEvent;
import com.github.marceloleite2604.pitanga.dto.event.attendeevoted.AttendeeVotedPayload;
import com.github.marceloleite2604.pitanga.mapper.AttendeeToDtoMapper;
import com.github.marceloleite2604.pitanga.mapper.RoomToDtoMapper;
import com.github.marceloleite2604.pitanga.mapper.UserToDtoMapper;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AttendeeVotedEventHandler extends AbstractEventHandler<AttendeeVotedPayload> {

    private final AttendeeToDtoMapper attendeeToDtoMapper;
    private final RoomToDtoMapper roomToDtoMapper;

    public AttendeeVotedEventHandler(PitangaService pitangaService, UserToDtoMapper userToDtoMapper, AttendeeToDtoMapper attendeeToDtoMapper, RoomToDtoMapper roomToDtoMapper) {
        super(pitangaService, EventType.ATTENDEE_VOTED, userToDtoMapper);
        this.attendeeToDtoMapper = attendeeToDtoMapper;
        this.roomToDtoMapper = roomToDtoMapper;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var incomingAttendeeVotedPayload = retrievePayload(incomingContext);

        var attendeeDao = incomingAttendeeVotedPayload.getAttendee();

        var attendee = attendeeToDtoMapper.mapFrom(attendeeDao);

        attendee = pitangaService.updateVoteForAttendee(attendee);

        var recipients = elaborateRecipients(attendee);

        attendeeDao = attendeeToDtoMapper.mapTo(attendee);
        var roomDao = roomToDtoMapper.mapTo(attendee.getRoom());

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
