package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.MaxRoomsReachedEvent;
import com.github.marceloleite2604.pitanga.model.event.RoomCreatedEvent;
import com.github.marceloleite2604.pitanga.model.mapper.RoomToDao;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomEventHandler extends AbstractEventHandler<UserDao> {

    private final RoomToDao roomToDao;

    private final UserToDao userToDao;

    public CreateRoomEventHandler(PitangaService pitangaService, RoomToDao roomToDao, UserToDao userToDao) {
        super(pitangaService, EventType.CREATE_ROOM, UserDao.class);
        this.roomToDao = roomToDao;
        this.userToDao = userToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var user = userToDao.mapFrom(retrievePayload(incomingContext));
        user.setSessionId(incomingContext.getSessionId());

        var createRoomResult = pitangaService.createRoom(user);

        var room = roomToDao.mapTo(createRoomResult.getRoom());

        var event = switch (createRoomResult.getStatus()) {
            case CREATED -> RoomCreatedEvent.builder()
                    .payload(room)
                    .build();
            case MAX_ROOMS_REACHED -> MaxRoomsReachedEvent.builder()
                    .build();
        };

        return OutgoingContext.builder()
                .event(event)
                .build();
    }
}
