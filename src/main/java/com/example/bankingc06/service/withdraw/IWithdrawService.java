package com.example.bankingc06.service.withdraw;

import com.example.bankingc06.model.Withdraw;
import com.example.bankingc06.service.IGeneralService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWithdrawService extends IGeneralService<Withdraw, Long> {
}
