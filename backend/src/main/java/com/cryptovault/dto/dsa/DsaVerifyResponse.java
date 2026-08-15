package com.cryptovault.dto.dsa;

public class DsaVerifyResponse {

    private boolean valid;

    public DsaVerifyResponse() {
    }

    public DsaVerifyResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
