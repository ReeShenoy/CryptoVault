package com.cryptovault.dto.dh;

public class DhKeyPairResponse {

    private String alicePublicKey;
    private String alicePrivateKey;
    private String bobPublicKey;
    private String bobPrivateKey;

    public DhKeyPairResponse() {
    }

    public DhKeyPairResponse(String alicePublicKey, String alicePrivateKey,
                              String bobPublicKey, String bobPrivateKey) {
        this.alicePublicKey = alicePublicKey;
        this.alicePrivateKey = alicePrivateKey;
        this.bobPublicKey = bobPublicKey;
        this.bobPrivateKey = bobPrivateKey;
    }

    public String getAlicePublicKey() {
        return alicePublicKey;
    }

    public void setAlicePublicKey(String alicePublicKey) {
        this.alicePublicKey = alicePublicKey;
    }

    public String getAlicePrivateKey() {
        return alicePrivateKey;
    }

    public void setAlicePrivateKey(String alicePrivateKey) {
        this.alicePrivateKey = alicePrivateKey;
    }

    public String getBobPublicKey() {
        return bobPublicKey;
    }

    public void setBobPublicKey(String bobPublicKey) {
        this.bobPublicKey = bobPublicKey;
    }

    public String getBobPrivateKey() {
        return bobPrivateKey;
    }

    public void setBobPrivateKey(String bobPrivateKey) {
        this.bobPrivateKey = bobPrivateKey;
    }
}
