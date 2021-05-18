package com.github.marceloleite2604.pitanga.model.event;

public class CreateUserEvent extends Event<Void> {

    public CreateUserEvent() {
        super(EventType.CREATE_USER, null);
    }
}
