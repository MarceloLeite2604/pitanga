package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.service.PitangaService;
import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.MaxUsersReachedEvent;
import com.github.marceloleite2604.pitanga.model.event.UserCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class CreateUserEventHandler extends AbstractEventHandler<User> {
    protected CreateUserEventHandler(PitangaService pitangaService) {
        super(pitangaService, EventType.CREATE_USER, User.class);
    }

    @Override
    protected Event<?> doHandle(IncomingContext incomingContext) {
        var createUserResult = pitangaService.createUser(incomingContext.getSessionId());

        return switch (createUserResult.getStatus()) {
            case CREATED -> UserCreatedEvent.builder()
                    .payload(createUserResult.getUser())
                    .build();
            case MAX_USERS_REACHED -> MaxUsersReachedEvent.builder()
                    .build();
        };
    }
}
