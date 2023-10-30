package com.example.bankingc06.repository;

import com.example.bankingc06.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransferRepository extends JpaRepository<Transfer, Long> {
}
