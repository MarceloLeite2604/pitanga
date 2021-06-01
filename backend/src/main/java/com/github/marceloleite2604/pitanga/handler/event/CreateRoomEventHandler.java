package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.MaxRoomsReachedEvent;
import com.github.marceloleite2604.pitanga.model.event.createroom.CreateRoomPayload;
import com.github.marceloleite2604.pitanga.model.event.roomcreated.RoomCreatedEvent;
import com.github.marceloleite2604.pitanga.model.event.roomcreated.RoomCreatedPayload;
import com.github.marceloleite2604.pitanga.model.mapper.RoomToDao;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomEventHandler extends AbstractEventHandler<CreateRoomPayload> {

    private final RoomToDao roomToDao;

    public CreateRoomEventHandler(PitangaService pitangaService, RoomToDao roomToDao, UserToDao userToDao) {
        super(pitangaService, EventType.CREATE_ROOM, userToDao);
        this.roomToDao = roomToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var createRoomPayload = retrievePayload(incomingContext);
        var user = userToDao.mapFrom(createRoomPayload.getUser());

        var createRoomResult = pitangaService.createRoom(user);

        var room = roomToDao.mapTo(createRoomResult.room());

        var event = switch (createRoomResult.status()) {
            case CREATED -> {

                var roomCreatedPayload = RoomCreatedPayload.builder()
                        .room(room)
                        .build();

                yield RoomCreatedEvent.builder()
                        .payload(roomCreatedPayload)
                        .build();
            }
            case MAX_ROOMS_REACHED -> MaxRoomsReachedEvent.builder()
                    .build();
        };

        return OutgoingContext.builder()
                .event(event)
                .build();
    }
}
