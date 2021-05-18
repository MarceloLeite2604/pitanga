package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.service.PitangaService;
import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.MaxRoomsReachedEvent;
import com.github.marceloleite2604.pitanga.model.event.RoomCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomEventHandler extends AbstractEventHandler<User> {

    public CreateRoomEventHandler(PitangaService pitangaService) {
        super(pitangaService, EventType.CREATE_ROOM, User.class);
    }

    @Override
    protected Event<?> doHandle(IncomingContext incomingContext) {
        var user = retrievePayload(incomingContext);

        var createRoomResult = pitangaService.createRoom(user);

        return switch (createRoomResult.getStatus()) {
            case CREATED -> RoomCreatedEvent.builder()
                    .payload(createRoomResult.getRoom())
                    .build();
            case MAX_ROOMS_REACHED -> MaxRoomsReachedEvent.builder()
                    .build();
        };
    }
}
