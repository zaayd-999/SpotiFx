package com.db.Callbacks;
import com.db.Classes.QueryResult;

@FunctionalInterface
public interface firstQueryCallback {
    void call(Exception error , QueryResult results);
}
