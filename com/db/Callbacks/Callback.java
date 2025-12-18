package com.db.Callbacks;

@FunctionalInterface
public interface Callback<T> {
    void call(Exception error , T result);
}