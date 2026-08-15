package com.cryptovault.controller;

import com.cryptovault.dto.rsa.*;
import com.cryptovault.service.OperationLogService;
import com.cryptovault.service.RsaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rsa")
public class RsaController {

    private final RsaService rsaService;
    private final OperationLogService operationLogService;

    public RsaController(RsaService rsaService, OperationLogService operationLogService) {
        this.rsaService = rsaService;
        this.operationLogService = operationLogService;
    }

    @PostMapping("/generate-keys")
    public ResponseEntity<RsaKeyPairResponse> generateKeys() {
        try {
            RsaKeyPairResponse response = rsaService.generateKeyPair();
            operationLogService.log("RSA", "GENERATE_KEYS", "SUCCESS");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            operationLogService.log("RSA", "GENERATE_KEYS", "FAILURE");
            throw e;
        }
    }

    @PostMapping("/encrypt")
    public ResponseEntity<RsaEncryptResponse> encrypt(@Valid @RequestBody RsaEncryptRequest request) {
        try {
            String ciphertext = rsaService.encrypt(request.getPlaintext(), request.getPublicKey());
            operationLogService.log("RSA", "ENCRYPT", "SUCCESS");
            return ResponseEntity.ok(new RsaEncryptResponse(ciphertext));
        } catch (RuntimeException e) {
            operationLogService.log("RSA", "ENCRYPT", "FAILURE");
            throw e;
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<RsaDecryptResponse> decrypt(@Valid @RequestBody RsaDecryptRequest request) {
        try {
            String plaintext = rsaService.decrypt(request.getCiphertext(), request.getPrivateKey());
            operationLogService.log("RSA", "DECRYPT", "SUCCESS");
            return ResponseEntity.ok(new RsaDecryptResponse(plaintext));
        } catch (RuntimeException e) {
            operationLogService.log("RSA", "DECRYPT", "FAILURE");
            throw e;
        }
    }
}
