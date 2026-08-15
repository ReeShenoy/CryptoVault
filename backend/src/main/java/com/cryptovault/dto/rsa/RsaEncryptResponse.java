package com.cryptovault.dto.rsa;

public class RsaEncryptResponse {

    private String ciphertext;

    public RsaEncryptResponse() {
    }

    public RsaEncryptResponse(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }
}
