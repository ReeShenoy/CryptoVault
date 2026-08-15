package com.cryptovault.dto.rsa;

public class RsaDecryptResponse {

    private String plaintext;

    public RsaDecryptResponse() {
    }

    public RsaDecryptResponse(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }
}
