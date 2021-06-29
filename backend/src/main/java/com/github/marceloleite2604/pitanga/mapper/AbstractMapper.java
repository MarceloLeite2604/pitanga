package com.github.marceloleite2604.pitanga.mapper;

import java.util.Objects;

public abstract class AbstractMapper<F, T> implements Mapper<F, T> {
    @Override
    public T mapTo(F from) {
        if (Objects.isNull(from)) {
            return null;
        }

        return doMapTo(from);
    }

    @Override
    public F mapFrom(T to) {

        if (Objects.isNull(to)) {
            return null;
        }

        return doMapFrom(to);
    }

    protected abstract T doMapTo(F from);

    protected abstract F doMapFrom(T to);
}
