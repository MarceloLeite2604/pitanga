package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.MaxRoomsReachedEvent;
import com.github.marceloleite2604.pitanga.dto.event.createroom.CreateRoomPayload;
import com.github.marceloleite2604.pitanga.dto.event.roomcreated.RoomCreatedEvent;
import com.github.marceloleite2604.pitanga.dto.event.roomcreated.RoomCreatedPayload;
import com.github.marceloleite2604.pitanga.mapper.RoomToDtoMapper;
import com.github.marceloleite2604.pitanga.mapper.UserToDtoMapper;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomEventHandler extends AbstractEventHandler<CreateRoomPayload> {

    private final RoomToDtoMapper roomToDtoMapper;

    public CreateRoomEventHandler(PitangaService pitangaService, RoomToDtoMapper roomToDtoMapper, UserToDtoMapper userToDtoMapper) {
        super(pitangaService, EventType.CREATE_ROOM, userToDtoMapper);
        this.roomToDtoMapper = roomToDtoMapper;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var createRoomPayload = retrievePayload(incomingContext);
        var user = userToDtoMapper.mapFrom(createRoomPayload.getUser());

        var createRoomResult = pitangaService.createRoom(user);

        var room = roomToDtoMapper.mapTo(createRoomResult.room());

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
