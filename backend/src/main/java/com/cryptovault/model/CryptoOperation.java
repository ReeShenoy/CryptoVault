package com.cryptovault.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_operations")
public class CryptoOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String algorithm; // RSA, DIFFIE_HELLMAN, DSA

    @Column(nullable = false, length = 50)
    private String operation; // GENERATE_KEYS, ENCRYPT, DECRYPT, KEY_EXCHANGE, SIGN, VERIFY

    @Column(nullable = false, length = 20)
    private String status; // SUCCESS, FAILURE

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public CryptoOperation() {
    }

    public CryptoOperation(String algorithm, String operation, String status, LocalDateTime timestamp) {
        this.algorithm = algorithm;
        this.operation = operation;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
