package com.cryptovault.dto.dh;

import jakarta.validation.constraints.NotBlank;

public class DhSharedSecretRequest {

    @NotBlank(message = "Alice's message must not be empty")
    private String aliceMessage;

    @NotBlank(message = "Bob's message must not be empty")
    private String bobMessage;

    public String getAliceMessage() {
        return aliceMessage;
    }

    public void setAliceMessage(String aliceMessage) {
        this.aliceMessage = aliceMessage;
    }

    public String getBobMessage() {
        return bobMessage;
    }

    public void setBobMessage(String bobMessage) {
        this.bobMessage = bobMessage;
    }
}