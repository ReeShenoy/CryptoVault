package com.cryptovault.controller;

import com.cryptovault.model.CryptoOperation;
import com.cryptovault.service.OperationLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    private final OperationLogService operationLogService;

    public OperationController(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @GetMapping
    public List<CryptoOperation> getAllOperations() {
        return operationLogService.getAll();
    }
}
