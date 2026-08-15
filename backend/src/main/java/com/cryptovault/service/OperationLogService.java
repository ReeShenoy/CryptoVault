package com.cryptovault.service;

import com.cryptovault.model.CryptoOperation;
import com.cryptovault.repository.CryptoOperationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperationLogService {

    private final CryptoOperationRepository repository;

    public OperationLogService(CryptoOperationRepository repository) {
        this.repository = repository;
    }

    public void log(String algorithm, String operation, String status) {
        CryptoOperation entry = new CryptoOperation(algorithm, operation, status, LocalDateTime.now());
        repository.save(entry);
    }

    public List<CryptoOperation> getAll() {
        return repository.findAllByOrderByTimestampDesc();
    }
}
