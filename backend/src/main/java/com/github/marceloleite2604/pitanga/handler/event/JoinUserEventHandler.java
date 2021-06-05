package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.MaxRoomsUsersReachedEvent;
import com.github.marceloleite2604.pitanga.dto.event.joinuser.JoinUserPayload;
import com.github.marceloleite2604.pitanga.dto.event.useralreadyinroom.UserAlreadyInRoomEvent;
import com.github.marceloleite2604.pitanga.dto.event.useralreadyinroom.UserAlreadyInRoomPayload;
import com.github.marceloleite2604.pitanga.dto.event.userjoined.UserJoinedEvent;
import com.github.marceloleite2604.pitanga.dto.event.userjoined.UserJoinedPayload;
import com.github.marceloleite2604.pitanga.mapper.RoomToDto;
import com.github.marceloleite2604.pitanga.mapper.UserToDto;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class JoinUserEventHandler extends AbstractEventHandler<JoinUserPayload> {

    private final RoomToDto roomToDto;

    public JoinUserEventHandler(PitangaService pitangaService, RoomToDto roomToDto, UserToDto userToDto) {
        super(pitangaService, EventType.JOIN_USER, userToDto);
        this.roomToDto = roomToDto;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var joinUserPayload = retrievePayload(incomingContext);

        var room = roomToDto.mapFrom(joinUserPayload.getRoom());
        var user = userToDto.mapFrom(joinUserPayload.getUser());

        var joinUserResult = pitangaService.joinUserIntoRoom(user, room);

        var attendee = joinUserResult.attendee();

        var roomDao = roomToDto.mapTo(attendee.getRoom());
        var userDao = userToDto.mapTo(attendee.getUser());

        var event = switch (joinUserResult.status()) {
            case USER_JOINED -> {

                var userJoinedPayload = UserJoinedPayload.builder()
                        .room(roomDao)
                        .build();

                yield UserJoinedEvent.builder()
                        .payload(userJoinedPayload)
                        .build();
            }
            case MAX_ROOM_USERS_REACHED -> MaxRoomsUsersReachedEvent.builder()
                    .build();
            case ALREADY_IN_ROOM -> {

                var userAlreadyInRoomPayload = UserAlreadyInRoomPayload.builder()
                        .user(userDao)
                        .room(roomDao)
                        .build();

                yield UserAlreadyInRoomEvent.builder()
                        .payload(userAlreadyInRoomPayload)
                        .build();
            }
        };

        var recipients = switch (joinUserResult.status()) {
            case USER_JOINED -> elaborateRecipients(room);
            case MAX_ROOM_USERS_REACHED, ALREADY_IN_ROOM -> new HashSet<>(Collections.singleton(userDao));
        };

        return OutgoingContext.builder()
                .recipients(recipients)
                .event(event)
                .build();
    }
}
