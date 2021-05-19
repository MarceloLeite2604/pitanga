package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.MaxUsersReachedEvent;
import com.github.marceloleite2604.pitanga.model.event.UserCreatedEvent;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

@Component
public class CreateUserEventHandler extends AbstractEventHandler<User> {

    private final UserToDao userToDao;

    public CreateUserEventHandler(PitangaService pitangaService, UserToDao userToDao) {
        super(pitangaService, EventType.CREATE_USER, User.class);
        this.userToDao = userToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var createUserResult = pitangaService.createUser(incomingContext.getSessionId());

        var user = userToDao.mapTo(createUserResult.getUser());

        var event = switch (createUserResult.getStatus()) {
            case CREATED -> UserCreatedEvent.builder()
                    .payload(user)
                    .build();
            case MAX_USERS_REACHED -> MaxUsersReachedEvent.builder()
                    .build();
        };

        return OutgoingContext.builder()
                .event(event)
                .build();
    }
}
