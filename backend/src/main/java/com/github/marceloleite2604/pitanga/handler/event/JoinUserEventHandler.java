package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.MaxRoomsUsersReachedEvent;
import com.github.marceloleite2604.pitanga.model.event.joinuser.JoinUserPayload;
import com.github.marceloleite2604.pitanga.model.event.useralreadyinroom.UserAlreadyInRoomEvent;
import com.github.marceloleite2604.pitanga.model.event.useralreadyinroom.UserAlreadyInRoomPayload;
import com.github.marceloleite2604.pitanga.model.event.userjoined.UserJoinedEvent;
import com.github.marceloleite2604.pitanga.model.event.userjoined.UserJoinedPayload;
import com.github.marceloleite2604.pitanga.model.mapper.RoomToDao;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class JoinUserEventHandler extends AbstractEventHandler<JoinUserPayload> {

    private final RoomToDao roomToDao;

    public JoinUserEventHandler(PitangaService pitangaService, RoomToDao roomToDao, UserToDao userToDao) {
        super(pitangaService, EventType.JOIN_USER, userToDao);
        this.roomToDao = roomToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var joinUserPayload = retrievePayload(incomingContext);

        var room = roomToDao.mapFrom(joinUserPayload.getRoom());
        var user = userToDao.mapFrom(joinUserPayload.getUser());

        var joinUserResult = pitangaService.joinUserIntoRoom(user, room);

        var attendee = joinUserResult.attendee();

        var roomDao = roomToDao.mapTo(attendee.getRoom());
        var userDao = userToDao.mapTo(attendee.getUser());

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
