package com.example.bankingc06.service.customer;

import com.example.bankingc06.model.Customer;
import com.example.bankingc06.model.Deposit;
import com.example.bankingc06.model.Transfer;
import com.example.bankingc06.model.Withdraw;
import com.example.bankingc06.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer,Long> {
    void deposit(Deposit deposit);
    void withdraw(Withdraw withdraw);

    List<Customer> findAllWithoutId(Long id);
    void processTransfer(Transfer transfer);
}
