package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.UserDto;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.userdropped.UserDroppedEvent;
import com.github.marceloleite2604.pitanga.dto.event.userdropped.UserDroppedPayload;
import com.github.marceloleite2604.pitanga.mapper.UserToDto;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class UserDroppedEventHandler extends AbstractEventHandler<UserDroppedPayload> {

    public UserDroppedEventHandler(PitangaService pitangaService, UserToDto userToDto) {
        super(pitangaService, EventType.USER_DROPPED, userToDto);
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var userDroppedPayload = retrievePayload(incomingContext);
        var optionalUser = pitangaService.retrieveUser(userDroppedPayload.getUser()
                .getId());

        UserDroppedEvent userDroppedEvent = null;

        Set<UserDto> recipients = null;

        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            recipients = Optional.ofNullable(user.getAttendee())
                    .map(this::elaborateRecipients)
                    .orElseGet(HashSet::new);

            pitangaService.deleteUser(user);

            userDroppedEvent = UserDroppedEvent.builder()
                    .payload(userDroppedPayload)
                    .build();
        }

        return OutgoingContext.builder()
                .event(userDroppedEvent)
                .recipients(recipients)
                .build();
    }
}
