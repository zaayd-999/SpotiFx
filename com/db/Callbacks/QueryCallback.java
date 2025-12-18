package com.db.Callbacks;

import com.db.Classes.QueryResults;

@FunctionalInterface
public interface QueryCallback {
    void call(Exception error , QueryResults result);
}
