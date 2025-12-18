package com.db.Callbacks;

@FunctionalInterface
public interface ConnectionCallback {
    void call(Exception error);
}