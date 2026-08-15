package com.cryptovault.controller;

import com.cryptovault.dto.dsa.*;
import com.cryptovault.service.DsaService;
import com.cryptovault.service.OperationLogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dsa")
public class DsaController {

    private final DsaService dsaService;
    private final OperationLogService operationLogService;

    public DsaController(DsaService dsaService, OperationLogService operationLogService) {
        this.dsaService = dsaService;
        this.operationLogService = operationLogService;
    }

    @PostMapping("/generate-keys")
    public ResponseEntity<DsaKeyPairResponse> generateKeys() {
        try {
            DsaKeyPairResponse response = dsaService.generateKeyPair();
            operationLogService.log("DSA", "GENERATE_KEYS", "SUCCESS");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            operationLogService.log("DSA", "GENERATE_KEYS", "FAILURE");
            throw e;
        }
    }

    @PostMapping("/sign")
    public ResponseEntity<DsaSignResponse> sign(@Valid @RequestBody DsaSignRequest request) {
        try {
            String signature = dsaService.sign(request.getMessage(), request.getPrivateKey());
            operationLogService.log("DSA", "SIGN", "SUCCESS");
            return ResponseEntity.ok(new DsaSignResponse(signature));
        } catch (RuntimeException e) {
            operationLogService.log("DSA", "SIGN", "FAILURE");
            throw e;
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<DsaVerifyResponse> verify(@Valid @RequestBody DsaVerifyRequest request) {
        try {
            boolean valid = dsaService.verify(request.getMessage(), request.getSignature(), request.getPublicKey());
            operationLogService.log("DSA", "VERIFY", valid ? "SUCCESS" : "FAILURE");
            return ResponseEntity.ok(new DsaVerifyResponse(valid));
        } catch (RuntimeException e) {
            operationLogService.log("DSA", "VERIFY", "FAILURE");
            throw e;
        }
    }
}
