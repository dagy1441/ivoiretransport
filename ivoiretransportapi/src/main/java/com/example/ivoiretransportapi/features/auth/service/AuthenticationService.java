package com.example.ivoiretransportapi.features.auth.service;

import com.example.ivoiretransportapi.features.auth.models.SigninRequest;
import com.example.ivoiretransportapi.features.auth.models.SigninResponse;
import com.example.ivoiretransportapi.features.auth.models.SignupRequest;
import com.example.ivoiretransportapi.features.user.User;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    public void signup(SignupRequest request) throws MessagingException;
    public SigninResponse signin(SigninRequest request);
    public void activateAccount(String token) throws MessagingException;

}
