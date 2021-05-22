package com.github.marceloleite2604.pitanga.service;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.attendee.AttendeeId;
import com.github.marceloleite2604.pitanga.properties.RoomProperties;
import com.github.marceloleite2604.pitanga.properties.UserProperties;
import com.github.marceloleite2604.pitanga.repository.AttendeeRepository;
import com.github.marceloleite2604.pitanga.repository.RoomRepository;
import com.github.marceloleite2604.pitanga.repository.UserRepository;
import com.github.marceloleite2604.pitanga.service.result.CreateRoomResult;
import com.github.marceloleite2604.pitanga.service.result.CreateUserResult;
import com.github.marceloleite2604.pitanga.service.result.JoinUserResult;
import com.github.marceloleite2604.pitanga.util.IconRetriever;
import com.github.marceloleite2604.pitanga.util.RoomIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PitangaService {

    private final RoomIdGenerator roomIdGenerator;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final AttendeeRepository attendeeRepository;

    private final RoomProperties roomProperties;

    private final UserProperties userProperties;

    private final IconRetriever iconRetriever;

    private final EntityManager entityManager;

    public CreateRoomResult createRoom(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User \"%s\" does not exist on service.");
        }

        if (roomRepository.count() >= roomProperties.getMaxRooms()) {
            return CreateRoomResult.builder()
                    .status(CreateRoomResult.Status.MAX_ROOMS_REACHED)
                    .build();
        }

        var room = Room.builder()
                .id(roomIdGenerator.generate())
                .lastUpdate(LocalDateTime.now())
                .build();

//        var attendeeId = AttendeeId.builder()
//                .userId(user.getId())
//                .roomId(room.getId())
//                .build();
//
//        var attendee = Attendee.builder()
//                .id(attendeeId)
//                .room(room)
//                .user(user)
//                .joinedAt(LocalDateTime.now())
//                .icon(iconRetriever.retrieve(room))
//                .build();
//
//        attendeeRepository.saveAndFlush(attendee);

        var attendee = createAttendee(user, room);

//        room = roomRepository.findById(room.getId())
//                .orElseThrow(IllegalStateException::new);
//
//        entityManager.refresh(room);

        return CreateRoomResult.builder()
                .status(CreateRoomResult.Status.CREATED)
                .room(attendee.getRoom())
                .build();

    }

    public CreateUserResult createUser(String sessionId) {

        if (userRepository.count() >= userProperties.getMaxUsers()) {
            return CreateUserResult.builder()
                    .status(CreateUserResult.Status.MAX_USERS_REACHED)
                    .build();
        }

        var user = User.builder()
                .id(UUID.fromString(sessionId))
                .build();
        user = userRepository.save(user);

        return CreateUserResult.builder()
                .status(CreateUserResult.Status.CREATED)
                .user(user)
                .build();
    }

    public JoinUserResult joinUserIntoRoom(User user, Room room) {
        var persistedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find a user with id \"%s\"", user.getId())));

        var persistedRoom = roomRepository.findById(room.getId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find a room with id \"%d\"", room.getId())));

        var optionalAttendee = persistedRoom.getAttendees()
                .stream()
                .filter(attendee -> attendee.getUser()
                        .equals(persistedUser))
                .findFirst();
        if (optionalAttendee.isPresent()) {
            return JoinUserResult.builder()
                    .status(JoinUserResult.Status.ALREADY_IN_ROOM)
                    .attendee(optionalAttendee.get())
//                    .user(persistedUser)
//                    .room(persistedRoom)
                    .build();
        }

        if (persistedRoom.getAttendees()
                .size() >= roomProperties.getMaxUsers()) {
            return JoinUserResult.builder()
                    .status(JoinUserResult.Status.MAX_ROOM_USERS_REACHED)
                    .build();
        }

        var attendee = createAttendee(persistedUser, persistedRoom);

//        room = roomRepository.findById(room.getId())
//                .orElseThrow(IllegalStateException::new);
//
//        entityManager.refresh(room);

//        persistedUser.setIcon(iconRetriever.retrieve(persistedRoom));
//
//        persistedRoom.getUsers()
//                .add(persistedUser);
//        persistedRoom = roomRepository.save(persistedRoom);
//
//        persistedUser.setRoom(persistedRoom);
//        persistedUser = userRepository.save(persistedUser);

        return JoinUserResult.builder()
                .status(JoinUserResult.Status.USER_JOINED)
                .attendee(attendee)
                .build();
    }

    private Attendee createAttendee(User user, Room room) {
        var attendeeId = AttendeeId.builder()
                .roomId(room.getId())
                .userId(user.getId())
                .build();

        var attendee = Attendee.builder()
                .id(attendeeId)
                .user(user)
                .room(room)
                .icon(iconRetriever.retrieve(room))
                .joinedAt(LocalDateTime.now())
                .build();

        attendee = attendeeRepository.saveAndFlush(attendee);
        entityManager.refresh(attendee);
        return attendee;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    ;

    public Optional<Room> findById(long roomId) {
        return roomRepository.findById(roomId);
    }

//    public Set<String> retrieveSessionIdsFromUsersOnRoom(Room room) {
//        return room.getAttendees()
//                .stream()
//                .map(Attendee::getUser)
//                .map(User::getId)
//                .map(UUID::toString)
//                .collect(Collectors.toCollection(HashSet::new));
//    }

    public Optional<User> retrieveUser(String id) {
        return userRepository.findById(UUID.fromString(id));
    }
}
