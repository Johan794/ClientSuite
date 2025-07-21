package com.seek.TalentSuite.application.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ERROR_CLIENT("This client already exist"),
    ERROR_CLIENT_NOT_FOUND("The client you're looking was not found"),
    ERROR_NO_CLIENTS("The list of clients is empty"),
    ERROR_AUTH("You can not access this route without authentication"),
    ERROR_LOGIN("Credentials for login are wrong"),
    ERROR_MISSING_ARGUMENT("Complete the required fields"),
    RUNTIME_ERROR("There is an error while running the app");

    private  final String message;

}
