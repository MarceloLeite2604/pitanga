package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.PitangaService;
import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingContext;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingEventType;
import com.github.marceloleite2604.pitanga.model.outgoing.OutgoingEvent;
import com.github.marceloleite2604.pitanga.model.outgoing.OutgoingEventType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateRoomEventHandler extends AbstractEventHandler {

    public CreateRoomEventHandler(PitangaService pitangaService) {
        super(pitangaService, IncomingEventType.CREATE_ROOM);
    }

    @Override
    protected OutgoingEvent<?> doHandle(IncomingContext incomingContext) {
        User user = retrievePayload(incomingContext, User.class);

        var room = pitangaService.createRoom(user);
        return OutgoingEvent.<Room>builder()
                .type(OutgoingEventType.ROOM_CREATED)
                .payload(room)
                .build();
    }
}
