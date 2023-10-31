package com.example.bankingc06.repository;

import com.example.bankingc06.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
}
