package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.PitangaService;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.UserJoinedEvent;
import com.github.marceloleite2604.pitanga.model.IncomingContext;
import org.springframework.stereotype.Component;

@Component
public class JoinUserEventHandler extends AbstractEventHandler<User> {
    protected JoinUserEventHandler(PitangaService pitangaService) {
        super(pitangaService, EventType.JOIN_USER, User.class);
    }

    @Override
    protected Event<?> doHandle(IncomingContext incomingContext) {
        var user = pitangaService.createUser(incomingContext.getSessionId());
        return UserJoinedEvent.<User>builder()
                .payload(user)
                .build();
    }
}
