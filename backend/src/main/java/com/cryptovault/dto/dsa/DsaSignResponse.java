package com.cryptovault.dto.dsa;

public class DsaSignResponse {

    private String signature;

    public DsaSignResponse() {
    }

    public DsaSignResponse(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
