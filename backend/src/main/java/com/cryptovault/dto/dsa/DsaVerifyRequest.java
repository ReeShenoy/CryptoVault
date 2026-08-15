package com.cryptovault.dto.dsa;

import jakarta.validation.constraints.NotBlank;

public class DsaVerifyRequest {

    @NotBlank(message = "Message must not be empty")
    private String message;

    @NotBlank(message = "Signature must not be empty")
    private String signature;

    @NotBlank(message = "Public key must not be empty")
    private String publicKey;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
