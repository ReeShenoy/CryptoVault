package com.cryptovault.dto.rsa;

import jakarta.validation.constraints.NotBlank;

public class RsaEncryptRequest {

    @NotBlank(message = "Plaintext must not be empty")
    private String plaintext;

    @NotBlank(message = "Public key must not be empty")
    private String publicKey;

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
