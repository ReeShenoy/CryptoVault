package com.cryptovault.dto.rsa;

import jakarta.validation.constraints.NotBlank;

public class RsaDecryptRequest {

    @NotBlank(message = "Ciphertext must not be empty")
    private String ciphertext;

    @NotBlank(message = "Private key must not be empty")
    private String privateKey;

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
