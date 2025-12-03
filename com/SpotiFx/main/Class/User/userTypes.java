package com.SpotiFx.main.Class.User;

public enum userTypes {
    LISTENER(0),
    ARTIST(1);

    private final int value;

    userTypes(int value) {
        this.value = value;
    }


    public int getValue() { return this.value; }
}
