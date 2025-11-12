package com.SpotiFx.db.Callbacks;
import com.SpotiFx.db.Classes.QueryResult;

@FunctionalInterface
public interface firstQueryCallback {
    void call(Exception error , QueryResult results);
}
