package com.github.marceloleite2604.pitanga.mapper;

public interface
Mapper<F, T> {

    T mapTo(F from);

    F mapFrom(T to);
}
