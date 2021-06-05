package com.github.marceloleite2604.pitanga.mapper;

import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class UserToDto implements Mapper<User, UserDto> {

    @Override
    public UserDto mapTo(User user) {

        if (Objects.isNull(user)) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId()
                        .toString())
                .build();
    }

    @Override
    public User mapFrom(UserDto userDao) {

        if (Objects.isNull(userDao)) {
            return null;
        }

        return User.builder()
                .id(UUID.fromString(userDao.getId()))
                .build();
    }
}
