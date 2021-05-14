package com.github.marceloleite2604.pitanga;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.repository.RoomRepository;
import com.github.marceloleite2604.pitanga.repository.UserRepository;
import com.github.marceloleite2604.pitanga.util.RoomIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PitangaService {

    private final RoomIdGenerator roomIdGenerator;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    public Room createRoom(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User \"%s\" does not exist on service.");
        }
        var room = Room.builder()
                .id(roomIdGenerator.generate())
                .lastUpdate(LocalDateTime.now())
                .user(user)
                .build();
        return roomRepository.save(room);

    }

    public User createUser(String sessionId) {
        var user = User.builder()
                .id(UUID.randomUUID())
                .sessionId(sessionId)
                .build();
        return userRepository.save(user);
    }

    public void includeUserIntoRoom(UUID userId, long roomId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find a user with id \"%s\"", userId)));
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find a room with id \"%d\"", roomId)));
        includeUserIntoRoom(user, room);
    }

    public void includeUserIntoRoom(User user, Room room) {
        room.getUsers()
                .add(user);
        roomRepository.save(room);
        user.setRoom(room);
        userRepository.save(user);
    }

    public void excludeUserBySessionId(String sessionId) {
        userRepository.findBySessionId(sessionId)
                .ifPresent(this::removeUserFromRoom);
    }

    private void removeUserFromRoom(User user) {
        if (Objects.nonNull(user.getRoom())) {
            user.getRoom()
                    .getUsers()
                    .remove(user);
        }
    }
}
