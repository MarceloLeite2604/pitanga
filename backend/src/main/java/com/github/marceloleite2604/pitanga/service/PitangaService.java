package com.github.marceloleite2604.pitanga.service;

import com.github.marceloleite2604.pitanga.service.result.CreateRoomResult;
import com.github.marceloleite2604.pitanga.service.result.CreateUserResult;
import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.properties.RoomProperties;
import com.github.marceloleite2604.pitanga.properties.UserProperties;
import com.github.marceloleite2604.pitanga.repository.RoomRepository;
import com.github.marceloleite2604.pitanga.repository.UserRepository;
import com.github.marceloleite2604.pitanga.service.result.JoinUserResult;
import com.github.marceloleite2604.pitanga.util.RoomIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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

    public JoinUserResult joinUserIntoRoom(UUID userId, long roomId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find a user with id \"%s\"", userId)));

        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find a room with id \"%d\"", roomId)));

        if (room.getUsers()
                .size() >= roomProperties.getMaxUsers()) {
            return JoinUserResult.builder()
                    .status(JoinUserResult.Status.MAX_ROOM_USERS_REACHED)
                    .build();
        }

        room.getUsers()
                .add(user);
        roomRepository.save(room);

        user.setRoom(room);
        user = userRepository.save(user);

        return JoinUserResult.builder()
                .user(user)
                .build();
    }

    public void excludeUserBySessionId(String sessionId) {
        userRepository.findBySessionId(sessionId)
                .ifPresent(this::removeUserFromRoom);
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

    public boolean checkRoomExists(long roomId) {
        return roomRepository.existsById(roomId);
    }
}
