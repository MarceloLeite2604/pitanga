package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.attendeevoted.AttendeeVotedEvent;
import com.github.marceloleite2604.pitanga.dto.event.attendeevoted.AttendeeVotedPayload;
import com.github.marceloleite2604.pitanga.mapper.AttendeeToDto;
import com.github.marceloleite2604.pitanga.mapper.RoomToDto;
import com.github.marceloleite2604.pitanga.mapper.UserToDto;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AttendeeVotedEventHandler extends AbstractEventHandler<AttendeeVotedPayload> {

    private final AttendeeToDto attendeeToDto;
    private final RoomToDto roomToDto;

    public AttendeeVotedEventHandler(PitangaService pitangaService, UserToDto userToDto, AttendeeToDto attendeeToDto, RoomToDto roomToDto) {
        super(pitangaService, EventType.ATTENDEE_VOTED, userToDto);
        this.attendeeToDto = attendeeToDto;
        this.roomToDto = roomToDto;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var incomingAttendeeVotedPayload = retrievePayload(incomingContext);

        var attendeeDao = incomingAttendeeVotedPayload.getAttendee();

        var attendee = attendeeToDto.mapFrom(attendeeDao);

        attendee = pitangaService.updateVoteForAttendee(attendee);

        var recipients = elaborateRecipients(attendee);

        attendeeDao = attendeeToDto.mapTo(attendee);
        var roomDao = roomToDto.mapTo(attendee.getRoom());

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
