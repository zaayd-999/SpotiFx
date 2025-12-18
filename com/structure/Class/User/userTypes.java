package com.structure.Class.User;

public enum userTypes {
    LISTENER(0),
    ARTIST(1);

    private final int value;

    userTypes(int value) {
        this.value = value;
    }


    public int getValue() { return this.value; }

    public userTypes getType(int value) {
        return switch (value) {
            case 0 -> LISTENER;
            case 1 -> ARTIST;
            default -> throw new IllegalArgumentException("Invalid UserTypes value: " + value);
        };
    }
}
