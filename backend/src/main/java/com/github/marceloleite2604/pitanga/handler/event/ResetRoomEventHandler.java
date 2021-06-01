package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.event.EmptyPayload;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.resetroom.ResetRoomEvent;
import com.github.marceloleite2604.pitanga.model.event.resetroom.ResetRoomPayload;
import com.github.marceloleite2604.pitanga.model.mapper.RoomToDao;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ResetRoomEventHandler extends AbstractEventHandler<EmptyPayload> {

    private final RoomToDao roomToDao;

    public ResetRoomEventHandler(PitangaService pitangaService, UserToDao userToDao, RoomToDao roomToDao) {
        super(pitangaService, EventType.RESET_ROOM, userToDao);
        this.roomToDao = roomToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var userId = UUID.fromString(incomingContext.sender()
                .getId());

        pitangaService.resetRoomWithUser(userId);

        var attendee = pitangaService.findMandatoryAttendeeByUserId(userId);
        var roomDao = roomToDao.mapTo(attendee.getRoom());

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
