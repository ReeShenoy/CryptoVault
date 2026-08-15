package com.cryptovault.dto.dh;

public class DhSharedSecretResponse {

    private String sharedSecret;
    private boolean match;
    private String encryptedAliceMessage;
    private String encryptedBobMessage;

    public DhSharedSecretResponse() {
    }

    public DhSharedSecretResponse(
            String sharedSecret,
            boolean match,
            String encryptedAliceMessage,
            String encryptedBobMessage
    ) {
        this.sharedSecret = sharedSecret;
        this.match = match;
        this.encryptedAliceMessage = encryptedAliceMessage;
        this.encryptedBobMessage = encryptedBobMessage;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getEncryptedAliceMessage() {
        return encryptedAliceMessage;
    }

    public void setEncryptedAliceMessage(String encryptedAliceMessage) {
        this.encryptedAliceMessage = encryptedAliceMessage;
    }

    public String getEncryptedBobMessage() {
        return encryptedBobMessage;
    }

    public void setEncryptedBobMessage(String encryptedBobMessage) {
        this.encryptedBobMessage = encryptedBobMessage;
    }
}