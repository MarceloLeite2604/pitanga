package com.github.marceloleite2604.pitanga.model.mapper;

public interface Mapper<F, T> {

    T mapTo(F from);

    F mapFrom(T to);
}
