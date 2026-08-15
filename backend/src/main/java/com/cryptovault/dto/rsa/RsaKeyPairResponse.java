package com.cryptovault.dto.rsa;

public class RsaKeyPairResponse {

    private String publicKey;
    private String privateKey;
    private int keySize;

    public RsaKeyPairResponse() {
    }

    public RsaKeyPairResponse(String publicKey, String privateKey, int keySize) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.keySize = keySize;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }
}
