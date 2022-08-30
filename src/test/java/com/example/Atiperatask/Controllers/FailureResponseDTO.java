package com.example.Atiperatask.Controllers;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class FailureResponseDTO implements Serializable {
    private int status;
    private String message;

    public FailureResponseDTO setStatus(int status) {
        this.status = status;
        return this;
    }

    public FailureResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }
}
