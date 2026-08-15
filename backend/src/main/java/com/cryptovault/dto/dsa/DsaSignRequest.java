package com.cryptovault.dto.dsa;

import jakarta.validation.constraints.NotBlank;

public class DsaSignRequest {

    @NotBlank(message = "Message must not be empty")
    private String message;

    @NotBlank(message = "Private key must not be empty")
    private String privateKey;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
