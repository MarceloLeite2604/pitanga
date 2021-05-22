package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.userdropped.UserDroppedEvent;
import com.github.marceloleite2604.pitanga.model.event.userdropped.UserDroppedPayload;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserDroppedEventHandler extends AbstractEventHandler<UserDroppedPayload> {

    private final UserToDao userToDao;

    public UserDroppedEventHandler(PitangaService pitangaService, UserToDao userToDao) {
        super(pitangaService, EventType.USER_DROPPED);
        this.userToDao = userToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var userDroppedPayload = retrievePayload(incomingContext);
        var optionalUser = pitangaService.retrieveUser(userDroppedPayload.getUser()
                .getId());

        UserDroppedEvent userDroppedEvent = null;

        Set<UserDao> recipients = null;

        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            recipients = Optional.ofNullable(user.getAttendee())
                    .map(Attendee::getRoom)
                    .map(Room::getAttendees)
                    .map(attendees ->
                            attendees.stream()
                                    .map(Attendee::getUser)
                                    .map(userToDao::mapTo)
                                    .collect(Collectors.toCollection(HashSet::new)))
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
