package com.example.ivoiretransportapi.core.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int errorCode;
    private  String errorMessage;
    private String devErrorMessage;
    private long timeStamp;
}
