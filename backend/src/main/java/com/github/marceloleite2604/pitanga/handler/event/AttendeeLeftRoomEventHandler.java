package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.attendeeleftroom.AttendeeLeftRoomEvent;
import com.github.marceloleite2604.pitanga.dto.event.attendeeleftroom.AttendeeLeftRoomPayload;
import com.github.marceloleite2604.pitanga.mapper.AttendeeToDtoMapper;
import com.github.marceloleite2604.pitanga.mapper.UserToDtoMapper;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class AttendeeLeftRoomEventHandler extends AbstractEventHandler<AttendeeLeftRoomPayload> {

    private final AttendeeToDtoMapper attendeeToDtoMapper;

    public AttendeeLeftRoomEventHandler(PitangaService pitangaService, UserToDtoMapper userToDtoMapper, AttendeeToDtoMapper attendeeToDtoMapper) {
        super(pitangaService, EventType.ATTENDEE_LEFT_ROOM, userToDtoMapper);
        this.attendeeToDtoMapper = attendeeToDtoMapper;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var incomingAttendeeLeftRoomPayload = retrievePayload(incomingContext);
        var attendeeDto = incomingAttendeeLeftRoomPayload.getAttendee();
        var attendee = pitangaService.findMandatoryAttendeeByUserId(attendeeDto.getUser()
                .getId());
        pitangaService.deleteAttendee(attendee);

        Attendee newRoomOwner = null;
        if (attendee.isRoomOwner()) {
            newRoomOwner = pitangaService.changeRoomOwner(attendee.getRoom());
        }

        var recipients = elaborateRecipients(attendee);

        var outgoingAttendeeLeftRoomPayload = AttendeeLeftRoomPayload.builder()
                .attendee(attendeeDto)
                .newRoomOwner(attendeeToDtoMapper.mapTo(newRoomOwner))
                .build();

        var attendeeLeftRoomEvent = AttendeeLeftRoomEvent.builder()
                .payload(outgoingAttendeeLeftRoomPayload)
                .build();

        return OutgoingContext.builder()
                .event(attendeeLeftRoomEvent)
                .recipients(recipients)
                .build();
    }
}
