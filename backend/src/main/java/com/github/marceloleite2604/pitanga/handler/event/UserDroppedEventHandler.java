package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.UserDto;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.userdropped.UserDroppedEvent;
import com.github.marceloleite2604.pitanga.dto.event.userdropped.UserDroppedPayload;
import com.github.marceloleite2604.pitanga.mapper.AttendeeToDto;
import com.github.marceloleite2604.pitanga.mapper.UserToDto;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class UserDroppedEventHandler extends AbstractEventHandler<UserDroppedPayload> {

    private final AttendeeToDto attendeeToDto;

    public UserDroppedEventHandler(PitangaService pitangaService, UserToDto userToDto, AttendeeToDto attendeeToDto) {
        super(pitangaService, EventType.USER_DROPPED, userToDto);
        this.attendeeToDto = attendeeToDto;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var incomingUserDroppedPayload = retrievePayload(incomingContext);
        var userDto = incomingUserDroppedPayload.getUser();
        var optionalUser = pitangaService.retrieveUser(userDto
                .getId());

        UserDroppedEvent userDroppedEvent = null;

        Set<UserDto> recipients = null;

        if (optionalUser.isPresent()) {
            var user = optionalUser.get();

            Attendee newRoomOwner = null;
            if (user.getAttendee()
                    .isRoomOwner()) {
                var room = user.getAttendee()
                        .getRoom();
                newRoomOwner = pitangaService.changeRoomOwner(room);
            }

            pitangaService.deleteUser(user);

            var outgoingUserDroppedPayload = UserDroppedPayload.builder()
                    .user(userDto)
                    .newRoomOwner(attendeeToDto.mapTo(newRoomOwner))
                    .build();

            recipients = Optional.ofNullable(user.getAttendee())
                    .map(this::elaborateRecipients)
                    .orElseGet(HashSet::new);

            userDroppedEvent = UserDroppedEvent.builder()
                    .payload(outgoingUserDroppedPayload)
                    .build();
        }

        return OutgoingContext.builder()
                .event(userDroppedEvent)
                .recipients(recipients)
                .build();
    }
}
