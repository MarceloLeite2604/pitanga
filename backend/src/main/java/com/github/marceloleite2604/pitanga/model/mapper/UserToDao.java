package com.github.marceloleite2604.pitanga.model.mapper;

import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class UserToDao implements Mapper<User, UserDao> {

    @Override
    public UserDao mapTo(User user) {

        if (Objects.isNull(user)) {
            return null;
        }

        return UserDao.builder()
                .id(user.getId()
                        .toString())
                .build();
    }

    @Override
    public User mapFrom(UserDao userDao) {

        if (Objects.isNull(userDao)) {
            return null;
        }

        return User.builder()
                .id(UUID.fromString(userDao.getId()))
                .build();
    }
}
