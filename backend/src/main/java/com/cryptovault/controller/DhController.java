package com.cryptovault.controller;

import com.cryptovault.dto.dh.DhSharedSecretRequest;
import com.cryptovault.dto.dh.DhSharedSecretResponse;
import com.cryptovault.service.DhService;
import com.cryptovault.service.OperationLogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dh")
public class DhController {

    private final DhService dhService;
    private final OperationLogService operationLogService;

    public DhController(
            DhService dhService,
            OperationLogService operationLogService
    ) {
        this.dhService = dhService;
        this.operationLogService = operationLogService;
    }

    @PostMapping("/process")
    public ResponseEntity<DhSharedSecretResponse> processMessages(
            @Valid @RequestBody DhSharedSecretRequest request
    ) {
        try {

            DhService.Result result =
                    dhService.processMessages(
                            request.getAliceMessage(),
                            request.getBobMessage()
                    );

            operationLogService.log(
                    "DIFFIE_HELLMAN",
                    "MESSAGE_PROCESSING",
                    "SUCCESS"
            );

            return ResponseEntity.ok(
                    new DhSharedSecretResponse(
                            result.aliceSharedSecret(),
                            result.aliceSharedSecret().equals(result.bobSharedSecret()),
                            result.encryptedAliceMessage(),
                            result.encryptedBobMessage()
                    )
            );

        } catch (Exception e) {

            operationLogService.log(
                    "DIFFIE_HELLMAN",
                    "MESSAGE_PROCESSING",
                    "FAILURE"
            );

            throw new RuntimeException(
                    "Diffie-Hellman message processing failed",
                    e
            );
        }
    }
}