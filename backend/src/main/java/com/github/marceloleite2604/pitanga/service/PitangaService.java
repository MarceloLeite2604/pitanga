package com.github.marceloleite2604.pitanga.service;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.Vote;
import com.github.marceloleite2604.pitanga.model.VotingStatus;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.attendee.AttendeeId;
import com.github.marceloleite2604.pitanga.properties.RoomProperties;
import com.github.marceloleite2604.pitanga.properties.UserProperties;
import com.github.marceloleite2604.pitanga.repository.AttendeeRepository;
import com.github.marceloleite2604.pitanga.repository.RoomRepository;
import com.github.marceloleite2604.pitanga.repository.UserRepository;
import com.github.marceloleite2604.pitanga.repository.VoteRepository;
import com.github.marceloleite2604.pitanga.service.result.CreateRoomResult;
import com.github.marceloleite2604.pitanga.service.result.CreateUserResult;
import com.github.marceloleite2604.pitanga.service.result.JoinUserResult;
import com.github.marceloleite2604.pitanga.util.IconRetriever;
import com.github.marceloleite2604.pitanga.util.RoomIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PitangaService {

    private final RoomIdGenerator roomIdGenerator;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final VoteRepository voteRepository;

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
                .votingStatus(VotingStatus.OPEN)
                .build();

        var attendee = createAttendee(user, room, true);

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
        var persistedUser = findMandatoryUserById(user.getId());

        var persistedRoom = findMandatoryRoomById(room.getId());

        var optionalAttendee = persistedRoom.getAttendees()
                .stream()
                .filter(attendee -> attendee.getUser()
                        .equals(persistedUser))
                .findFirst();
        if (optionalAttendee.isPresent()) {
            return JoinUserResult.builder()
                    .status(JoinUserResult.Status.ALREADY_IN_ROOM)
                    .attendee(optionalAttendee.get())
                    .build();
        }

        if (persistedRoom.getAttendees()
                .size() >= roomProperties.getMaxUsers()) {
            return JoinUserResult.builder()
                    .status(JoinUserResult.Status.MAX_ROOM_USERS_REACHED)
                    .build();
        }

        var attendee = createAttendee(persistedUser, persistedRoom);

        return JoinUserResult.builder()
                .status(JoinUserResult.Status.USER_JOINED)
                .attendee(attendee)
                .build();
    }

    private Attendee createAttendee(User user, Room room) {
        return createAttendee(user, room, false);
    }

    private Attendee createAttendee(User user, Room room, boolean roomOwner) {
        var attendeeId = AttendeeId.builder()
                .roomId(room.getId())
                .userId(user.getId())
                .build();

        var attendee = Attendee.builder()
                .id(attendeeId)
                .user(user)
                .room(room)
                .roomOwner(roomOwner)
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

    public Optional<Room> findRoomById(long roomId) {
        return roomRepository.findById(roomId);
    }

    public Optional<User> retrieveUser(String id) {
        return userRepository.findById(UUID.fromString(id));
    }

    private void updateVotingStatusForRoom(Room room) {
        var hasMissingVotes = attendeeRepository.findAllByRoomId(room.getId())
                .stream()
                .anyMatch(attendee -> Objects.isNull(attendee.getVote()));

        var votingStatus = hasMissingVotes ? VotingStatus.OPEN : VotingStatus.CLOSED;

        updateVotingStatusForRoom(room, votingStatus);
    }

    public void updateVotingStatusForRoom(Room room, VotingStatus votingStatus) {
        room.setVotingStatus(votingStatus);

        roomRepository.saveAndFlush(room);
    }

    public Attendee updateVoteForAttendee(Attendee attendee) {
        var persistedAttendee = findMandatoryAttendeeByUserId(attendee.getUser()
                .getId());

        var persistedVote = Optional.ofNullable(persistedAttendee.getVote())
                .orElse(Vote.builder()
                        .attendee(persistedAttendee)
                        .id(persistedAttendee.getId())
                        .build());

        persistedVote.setEffort(attendee.getVote()
                .getEffort());
        persistedVote.setValue(attendee.getVote()
                .getValue());

        voteRepository.saveAndFlush(persistedVote);
        entityManager.refresh(persistedAttendee);

        updateVotingStatusForRoom(persistedAttendee.getRoom());
        entityManager.refresh(persistedAttendee);

        return persistedAttendee;
    }

    private User findMandatoryUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find a user with id \"%s\"", id)));
    }

    private Room findMandatoryRoomById(long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find a room with id \"%d\"", id)));
    }

    public Attendee findMandatoryAttendeeByUserId(UUID userId) {
        return attendeeRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find an attendee associated with user \"%s\"", userId)));
    }

    public void closeVotingForRoomWithUser(UUID userId) {
        var attendee = findMandatoryAttendeeByUserId(userId);
        updateVotingStatusForRoom(attendee.getRoom(), VotingStatus.CLOSED);
        entityManager.refresh(attendee);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void resetRoomWithUser(UUID userId) {
        var attendee = findMandatoryAttendeeByUserId(userId);
        Optional.ofNullable(attendee.getVote())
                .map(Vote::getId)
                .map(AttendeeId::getRoomId)
                .ifPresent(voteRepository::deleteByIdRoomId);
        updateVotingStatusForRoom(attendee.getRoom(), VotingStatus.OPEN);
        entityManager.refresh(attendee);
    }
}
