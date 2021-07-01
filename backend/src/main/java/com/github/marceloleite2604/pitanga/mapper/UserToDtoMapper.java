package com.github.marceloleite2604.pitanga.mapper;

import com.github.marceloleite2604.pitanga.dto.UserDto;
import com.github.marceloleite2604.pitanga.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserToDtoMapper extends AbstractMapper<User, UserDto> {

    @Override
    public UserDto doMapTo(User user) {

        return UserDto.builder()
                .id(user.getId()
                        .toString())
                .build();
    }

    @Override
    public User doMapFrom(UserDto userDao) {

        return User.builder()
                .id(UUID.fromString(userDao.getId()))
                .build();
    }
}
