package com.UI.spotifx.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AuthState {

    private static final AuthState instance = new AuthState();

    private final BooleanProperty loggedIn = new SimpleBooleanProperty(false);

    private AuthState() {}

    public static AuthState getInstance() {
        return instance;
    }

    public boolean isLoggedIn() {
        return loggedIn.get();
    }

    public void login() {
        loggedIn.set(true);
    }

    public void logout() {
        loggedIn.set(false);
    }

    public BooleanProperty loggedInProperty() {
        return loggedIn;
    }
}

