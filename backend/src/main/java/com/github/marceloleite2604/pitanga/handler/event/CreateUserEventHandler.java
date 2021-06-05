package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.MaxUsersReachedEvent;
import com.github.marceloleite2604.pitanga.dto.event.createuser.CreateUserPayload;
import com.github.marceloleite2604.pitanga.dto.event.usercreated.UserCreatedEvent;
import com.github.marceloleite2604.pitanga.dto.event.usercreated.UserCreatedPayload;
import com.github.marceloleite2604.pitanga.mapper.UserToDto;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

@Component
public class CreateUserEventHandler extends AbstractEventHandler<CreateUserPayload> {

    public CreateUserEventHandler(PitangaService pitangaService, UserToDto userToDto) {
        super(pitangaService, EventType.CREATE_USER, userToDto);
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var createUserPayload = retrievePayload(incomingContext);

        var createUserResult = pitangaService.createUser(createUserPayload.getUser()
                .getId());

        var user = userToDto.mapTo(createUserResult.user());

        var event = switch (createUserResult.status()) {
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
