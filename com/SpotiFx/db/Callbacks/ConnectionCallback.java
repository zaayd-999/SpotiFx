package com.SpotiFx.db.Callbacks;

@FunctionalInterface
public interface ConnectionCallback {
    void call(Exception error);
}