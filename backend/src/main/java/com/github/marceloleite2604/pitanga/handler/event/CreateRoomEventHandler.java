package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.PitangaService;
import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.RoomCreatedEvent;
import com.github.marceloleite2604.pitanga.model.IncomingContext;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomEventHandler extends AbstractEventHandler<User> {

    public CreateRoomEventHandler(PitangaService pitangaService) {
        super(pitangaService, EventType.CREATE_ROOM, User.class);
    }

    @Override
    protected Event<?> doHandle(IncomingContext incomingContext) {
        var user = retrievePayload(incomingContext);

        var room = pitangaService.createRoom(user);
        return RoomCreatedEvent.<Room>builder()
                .payload(room)
                .build();
    }
}
