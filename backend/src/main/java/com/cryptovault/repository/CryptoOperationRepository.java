package com.cryptovault.repository;

import com.cryptovault.model.CryptoOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoOperationRepository extends JpaRepository<CryptoOperation, Long> {
    List<CryptoOperation> findAllByOrderByTimestampDesc();
}
