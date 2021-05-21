package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.UserDroppedEvent;
import com.github.marceloleite2604.pitanga.model.mapper.UserToDao;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDroppedEventHandler extends AbstractEventHandler<UserDao> {

    private final UserToDao userToDao;

    public UserDroppedEventHandler(PitangaService pitangaService, UserToDao userToDao) {
        super(pitangaService, EventType.USER_DROPPED);
        this.userToDao = userToDao;
    }

    @Override
    protected OutgoingContext doHandle(IncomingContext incomingContext) {
        var userDao = retrievePayload(incomingContext);
        var optionalUser = pitangaService.retrieveUser(userDao.getId());

        UserDroppedEvent userDroppedEvent = null;

        Set<UserDao> recipients = null;

        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            var room = user.getRoom();
            pitangaService.deleteUser(user);

            recipients = Optional.ofNullable(room)
                    .map(Room::getUsers)
                    .map(users ->
                            users.stream()
                                    .map(userToDao::mapTo)
                                    .collect(Collectors.toCollection(HashSet::new)))
                    .orElseGet(HashSet::new);

            userDroppedEvent = UserDroppedEvent.builder()
                    .payload(userDao)
                    .build();
        }

        return OutgoingContext.builder()
                .event(userDroppedEvent)
                .recipients(recipients)
                .build();
    }
}
