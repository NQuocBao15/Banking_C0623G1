package com.example.bankingc06.repository;

import com.example.bankingc06.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWithdrawRepository extends JpaRepository<Withdraw, Long> {
}
