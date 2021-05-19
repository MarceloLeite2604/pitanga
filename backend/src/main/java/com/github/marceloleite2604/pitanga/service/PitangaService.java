package com.github.marceloleite2604.pitanga.service;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.properties.RoomProperties;
import com.github.marceloleite2604.pitanga.properties.UserProperties;
import com.github.marceloleite2604.pitanga.repository.RoomRepository;
import com.github.marceloleite2604.pitanga.repository.UserRepository;
import com.github.marceloleite2604.pitanga.service.result.CreateRoomResult;
import com.github.marceloleite2604.pitanga.service.result.CreateUserResult;
import com.github.marceloleite2604.pitanga.service.result.JoinUserResult;
import com.github.marceloleite2604.pitanga.util.RoomIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PitangaService {

    private final RoomIdGenerator roomIdGenerator;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final RoomProperties roomProperties;

    private final UserProperties userProperties;

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
                .user(user)
                .build();

        room = roomRepository.save(room);

        user.setRoom(room);

        userRepository.save(user);

        return CreateRoomResult.builder()
                .status(CreateRoomResult.Status.CREATED)
                .room(room)
                .build();

    }

    public CreateUserResult createUser(String sessionId) {

        if (userRepository.count() >= userProperties.getMaxUsers()) {
            return CreateUserResult.builder()
                    .status(CreateUserResult.Status.MAX_USERS_REACHED)
                    .build();
        }

        var user = User.builder()
                .id(UUID.randomUUID())
                .sessionId(sessionId)
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

        if (persistedRoom.getUsers()
                .size() >= roomProperties.getMaxUsers()) {
            return JoinUserResult.builder()
                    .status(JoinUserResult.Status.MAX_ROOM_USERS_REACHED)
                    .build();
        }

        persistedRoom.getUsers()
                .add(persistedUser);
        persistedRoom = roomRepository.save(persistedRoom);

        persistedUser.setRoom(persistedRoom);
        persistedUser = userRepository.save(persistedUser);

        return JoinUserResult.builder()
                .status(JoinUserResult.Status.USER_JOINED)
                .user(persistedUser)
                .room(persistedRoom)
                .build();
    }

    public void excludeUserBySessionId(String sessionId) {
        userRepository.findBySessionId(sessionId)
                .ifPresent(user -> {
                    removeUserFromRoom(user);
                    userRepository.delete(user);
                });
    }

    private void removeUserFromRoom(User user) {
        var room = user.getRoom();
        if (Objects.nonNull(room)) {
            room.getUsers()
                    .remove(user);
            if (CollectionUtils.isEmpty(room.getUsers())) {
                roomRepository.delete(room);
            }
        }
    }

    public Optional<Room> findById(long roomId) {
        return roomRepository.findById(roomId);
    }

    public Set<String> retrieveSessionIdsFromUsersOnRoom(Room room) {
        return room.getUsers()
                .stream()
                .map(User::getSessionId)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
