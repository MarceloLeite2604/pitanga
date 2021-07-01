package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.checkroomexists.CheckRoomExistsEvent;
import com.github.marceloleite2604.pitanga.dto.event.checkroomexists.CheckRoomExistsPayload;
import com.github.marceloleite2604.pitanga.mapper.RoomToDtoMapper;
import com.github.marceloleite2604.pitanga.mapper.UserToDtoMapper;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

@Component
public class CheckRoomExistsEventHandler extends AbstractEventHandler<CheckRoomExistsPayload> {

    private final RoomToDtoMapper roomToDtoMapper;

    public CheckRoomExistsEventHandler(PitangaService pitangaService, UserToDtoMapper userToDtoMapper, RoomToDtoMapper roomToDtoMapper) {
        super(pitangaService, EventType.CHECK_ROOM_EXISTS, userToDtoMapper);
        this.roomToDtoMapper = roomToDtoMapper;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var checkRoomExistsPayload = retrievePayload(incomingContext);
        var optionalRoom = pitangaService.findRoomById(checkRoomExistsPayload.getRoom()
                .getId());

        var room = roomToDtoMapper.mapTo(optionalRoom.orElse(null));

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
