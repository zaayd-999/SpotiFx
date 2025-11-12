package com.SpotiFx.db.Callbacks;

import com.SpotiFx.db.Classes.QueryResults;

@FunctionalInterface
public interface QueryCallback {
    void call(Exception error , QueryResults result);
}
