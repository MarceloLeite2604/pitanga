package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.PitangaService;
import com.github.marceloleite2604.pitanga.model.*;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomEventHandler extends AbstractEventHandler {

    private final PitangaService pitangaService;

    public CreateRoomEventHandler(PitangaService pitangaService) {
        super(IncomingEventType.CREATE_ROOM);
        this.pitangaService = pitangaService;
    }

    @Override
    protected OutgoingEvent<?> doHandle(IncomingEvent<?> incomingEvent) {
        var room = pitangaService.createRoom();
        return OutgoingEvent.<Room>builder()
                .type(OutgoingEventType.ROOM_CREATED)
                .payload(room)
                .build();
    }
}
