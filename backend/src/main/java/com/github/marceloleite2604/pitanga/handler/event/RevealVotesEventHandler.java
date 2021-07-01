package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.UserDto;
import com.github.marceloleite2604.pitanga.dto.event.EmptyPayload;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.mapper.UserToDtoMapper;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
public class RevealVotesEventHandler extends AbstractEventHandler<EmptyPayload> {

    public RevealVotesEventHandler(PitangaService pitangaService, UserToDtoMapper userToDtoMapper) {
        super(pitangaService, EventType.REVEAL_VOTES, userToDtoMapper);
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var userId = UUID.fromString(incomingContext.sender()
                .getId());

        pitangaService.closeVotingForRoomWithUser(userId);

        var attendee = pitangaService.findMandatoryAttendeeByUserId(userId);
        Set<UserDto> recipients = elaborateRecipients(attendee);

        return OutgoingContext.builder()
                .event(incomingContext.event())
                .recipients(recipients)
                .build();
    }
}
