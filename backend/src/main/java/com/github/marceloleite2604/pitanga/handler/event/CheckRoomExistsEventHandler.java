package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.service.PitangaService;
import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.checkroomexists.CheckRoomExists;
import com.github.marceloleite2604.pitanga.model.event.checkroomexists.CheckRoomExistsPayload;
import org.springframework.stereotype.Component;

@Component
public class CheckRoomExistsEventHandler extends AbstractEventHandler<CheckRoomExistsPayload>{

    public CheckRoomExistsEventHandler(PitangaService pitangaService) {
        super(pitangaService, EventType.CHECK_ROOM_EXISTS, CheckRoomExistsPayload.class);
    }

    @Override
    protected Event<?> doHandle(IncomingContext incomingContext) {
        var checkRoomExistsPayload = retrievePayload(incomingContext);
        checkRoomExistsPayload.setExists(pitangaService.checkRoomExists(checkRoomExistsPayload.getId()));
        return CheckRoomExists.builder()
                .payload(checkRoomExistsPayload)
                .build();
    }
}
