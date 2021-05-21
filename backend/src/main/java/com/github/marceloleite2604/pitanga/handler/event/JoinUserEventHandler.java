package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.MaxRoomsUsersReachedEvent;
import com.github.marceloleite2604.pitanga.model.event.joinuser.JoinUserPayload;
import com.github.marceloleite2604.pitanga.model.event.userjoined.UserJoinedEvent;
import com.github.marceloleite2604.pitanga.model.event.userjoined.UserJoinedPayload;
import com.github.marceloleite2604.pitanga.model.mapper.RoomToDao;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class JoinUserEventHandler extends AbstractEventHandler<JoinUserPayload> {

    private final RoomToDao roomToDao;

    private final UserToDao userToDao;

    public JoinUserEventHandler(PitangaService pitangaService, RoomToDao roomToDao, UserToDao userToDao) {
        super(pitangaService, EventType.JOIN_USER);
        this.roomToDao = roomToDao;
        this.userToDao = userToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var joinUserPayload = retrievePayload(incomingContext);

        var room = roomToDao.mapFrom(joinUserPayload.getRoom());
        var user = userToDao.mapFrom(joinUserPayload.getUser());
//        user.setSessionId(incomingContext.getSessionId());

        var joinUserResult = pitangaService.joinUserIntoRoom(user, room);

        room = joinUserResult.getRoom();
        user = joinUserResult.getUser();

        var roomDao = roomToDao.mapTo(room);
        var userDao = userToDao.mapTo(user);

        var event = switch (joinUserResult.getStatus()) {
            case USER_JOINED -> {
                var userJoinedPayload = UserJoinedPayload.builder()
                        .user(userDao)
                        .room(roomDao)
                        .build();

                yield UserJoinedEvent.builder()
                        .payload(userJoinedPayload)
                        .build();
            }
            case MAX_ROOM_USERS_REACHED -> MaxRoomsUsersReachedEvent.builder()
                    .build();
        };

        var recipients = switch (joinUserResult.getStatus()) {
            case USER_JOINED -> room.getUsers()
                        .stream()
                        .map(userToDao::mapTo)
                        .collect(Collectors.toSet());
            case MAX_ROOM_USERS_REACHED -> new HashSet<>(Collections.singleton(userDao));
        };

        return OutgoingContext.builder()
                .recipients(recipients)
                .event(event)
                .build();
    }
}
