package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.MaxUsersReachedEvent;
import com.github.marceloleite2604.pitanga.model.event.createuser.CreateUserPayload;
import com.github.marceloleite2604.pitanga.model.event.usercreated.UserCreatedEvent;
import com.github.marceloleite2604.pitanga.model.event.usercreated.UserCreatedPayload;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

@Component
public class CreateUserEventHandler extends AbstractEventHandler<CreateUserPayload> {

    private final UserToDao userToDao;

    public CreateUserEventHandler(PitangaService pitangaService, UserToDao userToDao) {
        super(pitangaService, EventType.CREATE_USER);
        this.userToDao = userToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var createUserPayload = retrievePayload(incomingContext);

        var createUserResult = pitangaService.createUser(createUserPayload.getUser()
                .getId());

        var user = userToDao.mapTo(createUserResult.getUser());

        var event = switch (createUserResult.getStatus()) {
            case CREATED -> {
                var userCreatedPayload = UserCreatedPayload.builder()
                        .user(user)
                        .build();

                yield UserCreatedEvent.builder()
                        .payload(userCreatedPayload)
                        .build();
            }
            case MAX_USERS_REACHED -> MaxUsersReachedEvent.builder()
                    .build();
        };

        return OutgoingContext.builder()
                .event(event)
                .build();
    }
}
