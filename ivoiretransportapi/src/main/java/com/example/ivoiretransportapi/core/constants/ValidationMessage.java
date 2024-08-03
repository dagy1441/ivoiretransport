package com.example.ivoiretransportapi.core.constants;

import lombok.Getter;

@Getter
public enum ValidationMessage {
    INCORRECT_EMAIL_FORMAT("Email is not well formatted"),
    INCORRECT_CURRENT_PASSWORD("Current password is incorrect"),
    ;
    private final String message;
    ValidationMessage(String message) {

        this.message = message;

    }
}
