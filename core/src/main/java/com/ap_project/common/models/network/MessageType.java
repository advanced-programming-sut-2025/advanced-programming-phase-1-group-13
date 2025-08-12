package com.ap_project.common.models.network;

public enum MessageType {
    SIGNUP,
    LOGIN,
    LOGIN_SUCCESS,
    LOGIN_ERROR,
    REQUESTS_USERS_INFO,
    USERS_INFO,
    CREATE_LOBBY,
    REFRESH_LOBBIES,
    JOIN_LOBBY,
    LEAVE_LOBBY,
    REQUEST_LOBBY_INFO,
    LOBBY_LIST,
    ERROR,
    LOBBY_CREATED,
    JOIN_SUCCESS,
    LOBBY_INFO,
    START_GAME,
    START_CHOOSE_MAP,
    CHOSE_MAP,
    GO_TO_GAME
}
