package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.PitangaService;
import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingContext;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingEventType;
import com.github.marceloleite2604.pitanga.model.outgoing.OutgoingEvent;
import com.github.marceloleite2604.pitanga.model.outgoing.OutgoingEventType;
import org.springframework.stereotype.Component;

@Component
public class JoinUserEventHandler extends AbstractEventHandler{
    protected JoinUserEventHandler(PitangaService pitangaService) {
        super(pitangaService, IncomingEventType.JOIN_USER);
    }

    @Override
    protected OutgoingEvent<?> doHandle(IncomingContext incomingContext) {
        User user = pitangaService.createUser(incomingContext.getSessionId());
        return OutgoingEvent.<User>builder()
                .type(OutgoingEventType.USER_JOINED)
                .payload(user)
                .build();
    }
}
