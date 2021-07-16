package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.event.EmptyPayload;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.resetroom.ResetRoomEvent;
import com.github.marceloleite2604.pitanga.dto.event.resetroom.ResetRoomPayload;
import com.github.marceloleite2604.pitanga.mapper.RoomToDtoMapper;
import com.github.marceloleite2604.pitanga.mapper.UserToDtoMapper;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ResetRoomEventHandler extends AbstractEventHandler<EmptyPayload> {

    private final RoomToDtoMapper roomToDtoMapper;

    public ResetRoomEventHandler(PitangaService pitangaService, UserToDtoMapper userToDtoMapper, RoomToDtoMapper roomToDtoMapper) {
        super(pitangaService, EventType.RESET_ROOM, userToDtoMapper);
        this.roomToDtoMapper = roomToDtoMapper;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var userId = incomingContext.sender()
                .getId();

        pitangaService.resetRoomWithUser(userId);

        var attendee = pitangaService.findMandatoryAttendeeByUserId(userId);
        var roomDao = roomToDtoMapper.mapTo(attendee.getRoom());

        var recipients = elaborateRecipients(attendee);

        var resetRoomPayload = ResetRoomPayload.builder()
                .room(roomDao)
                .build();

        var resetRoomEvent = ResetRoomEvent.builder()
                .payload(resetRoomPayload)
                .build();

        return OutgoingContext.builder()
                .event(resetRoomEvent)
                .recipients(recipients)
                .build();
    }
}
