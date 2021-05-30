package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.checkroomexists.CheckRoomExistsEvent;
import com.github.marceloleite2604.pitanga.model.event.checkroomexists.CheckRoomExistsPayload;
import com.github.marceloleite2604.pitanga.model.mapper.RoomToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

@Component
public class CheckRoomExistsEventHandler extends AbstractEventHandler<CheckRoomExistsPayload> {

    private final RoomToDao roomToDao;

    public CheckRoomExistsEventHandler(PitangaService pitangaService, RoomToDao roomToDao) {
        super(pitangaService, EventType.CHECK_ROOM_EXISTS);
        this.roomToDao = roomToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var checkRoomExistsPayload = retrievePayload(incomingContext);
        var optionalRoom = pitangaService.findRoomById(checkRoomExistsPayload.getRoom()
                .getId());

        var room = roomToDao.mapTo(optionalRoom.orElse(null));

        checkRoomExistsPayload = CheckRoomExistsPayload.builder()
                .exists(optionalRoom.isPresent())
                .room(room)
                .build();

        var checkRoomExists = CheckRoomExistsEvent.builder()
                .payload(checkRoomExistsPayload)
                .build();

        return OutgoingContext.builder()
                .event(checkRoomExists)
                .build();
    }
}
