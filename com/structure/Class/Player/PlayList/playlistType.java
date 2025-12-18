package com.structure.Class.Player.PlayList;

public enum playlistType {
    Album(0),
    UserPlayList(1);

    private final int value;

    playlistType(int value) {
        this.value = value;
    }

    public int getValue() { return this.value; }

    public playlistType getType(int value) {
        return switch (value) {
            case 0 -> Album;
            case 1 -> UserPlayList;
            default -> throw new IllegalArgumentException("Invalid playlistType value: " + value);
        };
    }
}
