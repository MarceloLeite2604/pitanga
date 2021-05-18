package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.event.*;
import com.github.marceloleite2604.pitanga.model.event.joinuser.JoinUserPayload;
import com.github.marceloleite2604.pitanga.model.event.userjoined.UserJoinedEvent;
import com.github.marceloleite2604.pitanga.model.event.userjoined.UserJoinedPayload;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import com.github.marceloleite2604.pitanga.service.result.JoinUserResult;
import org.springframework.stereotype.Component;

@Component
public class JoinUserEventHandler extends AbstractEventHandler<JoinUserPayload> {

    public JoinUserEventHandler(PitangaService pitangaService) {
        super(pitangaService, EventType.JOIN_USER, JoinUserPayload.class);
    }

    @Override
    protected Event<?> doHandle(IncomingContext incomingContext) {
        var joinUserPayload = retrievePayload(incomingContext);

        JoinUserResult joinUserResult = pitangaService.joinUserIntoRoom(joinUserPayload.getUser()
                .getId(), joinUserPayload.getRoom()
                .getId());

        return switch (joinUserResult.getStatus()) {
            case USER_JOINED -> {
                var userJoinedPayload = UserJoinedPayload.builder()
                        .user(joinUserPayload.getUser())
                        .room(joinUserPayload.getRoom())
                        .build();

                yield UserJoinedEvent.builder()
                        .payload(userJoinedPayload)
                        .build();
            }
            case MAX_ROOM_USERS_REACHED -> MaxRoomsUsersReachedEvent.builder()
                    .build();
        };
    }
}
