package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.EmptyPayload;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
public class RevealVotesEventHandler extends AbstractEventHandler<EmptyPayload> {

    public RevealVotesEventHandler(PitangaService pitangaService, UserToDao userToDao) {
        super(pitangaService, EventType.REVEAL_VOTES, userToDao);
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var userId = UUID.fromString(incomingContext.sender()
                .getId());

        pitangaService.closeVotingForRoomWithUser(userId);

        var attendee = pitangaService.findMandatoryAttendeeByUserId(userId);
        Set<UserDao> recipients = elaborateRecipients(attendee);

        return OutgoingContext.builder()
                .event(incomingContext.event())
                .recipients(recipients)
                .build();
    }
}
