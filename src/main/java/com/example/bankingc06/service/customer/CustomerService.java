package com.example.bankingc06.service.customer;

import com.example.bankingc06.model.Customer;
import com.example.bankingc06.model.Deposit;
import com.example.bankingc06.model.Transfer;
import com.example.bankingc06.model.Withdraw;
import com.example.bankingc06.repository.CustomerRepository;
import com.example.bankingc06.repository.DepositRepository;
import com.example.bankingc06.repository.TransferRepository;
import com.example.bankingc06.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private WithdrawRepository withdrawRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Override
    public List<Customer> findAllByDeleted(boolean deleted) {
        return customerRepository.findAllByDeleted(deleted);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public void create(Customer customer) {
        customer.setBalance(BigDecimal.ZERO);
        customer.setDeleted(false);

        customerRepository.save(customer);
    }

    @Override
    public void update(Long id, Customer customer) {
        Optional<Customer> existingCustomer = Optional.ofNullable(findById(id));
        Customer customerUpdate = existingCustomer.get();
        customerUpdate.setFullName(customer.getFullName());
        customerUpdate.setEmail(customer.getEmail());
        customerUpdate.setPhone(customer.getPhone());
        customerUpdate.setAddress(customer.getAddress());
        customerRepository.save(customerUpdate);
    }

    @Override
    public void removeById(Long id) {
        Optional<Customer> existingCustomer = Optional.ofNullable(findById(id));
        Customer customer = existingCustomer.get();
        customer.setDeleted(true);
        customerRepository.save(customer);
    }

    @Override
    public void deposit(Deposit deposit) {
        depositRepository.save(deposit);
        BigDecimal newBalance = deposit.getTransactionAmount().add(deposit.getCustomer().getBalance());
        deposit.getCustomer().setBalance(newBalance);

        deposit.setCreateAt(LocalDateTime.now());

        customerRepository.save(deposit.getCustomer());
    }

    @Override
    public void withdraw(Withdraw withdraw) {
        withdrawRepository.save(withdraw);
        BigDecimal newBalance = withdraw.getCustomer().getBalance().subtract(withdraw.getAmount());
        withdraw.getCustomer().setBalance(newBalance);

        withdraw.setCreateAt(LocalDateTime.now());

        customerRepository.save(withdraw.getCustomer());
    }

    @Override
    public List<Customer> findAllWithoutId(Long id, boolean deleted) {
        List<Customer> allCustomers = customerRepository.findAllByDeleted(deleted);
        List<Customer> customers = allCustomers.stream()
                .filter(customer -> !customer.getId().equals(id))
                .collect(Collectors.toList());
        return customers;
    }

    @Override
    public void processTransfer(Transfer transfer) {
        BigDecimal senderBalance = transfer.getSender().getBalance();
        BigDecimal transferAmount = transfer.getTransferAmount();

        transfer.setFeesAmount(transfer.getTransactionAmount().subtract(transfer.getTransferAmount()));
        BigDecimal feesAmount = transfer.getFeesAmount();

        Customer sender = transfer.getSender();
        Customer recipient = transfer.getRecipient();


        BigDecimal newSenderBalance = senderBalance.subtract(transferAmount).subtract(feesAmount);
        sender.setBalance(newSenderBalance);

        BigDecimal newRecipientBalance = transfer.getRecipient().getBalance().add(transferAmount);
        recipient.setBalance(newRecipientBalance);

        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transferAmount);

        customerRepository.save(sender);
        customerRepository.save(recipient);

        transfer.setCreateAt(LocalDateTime.now());

        transferRepository.save(transfer);
    }

//    private BigDecimal calculateFees(BigDecimal transferAmount) {
//        BigDecimal feePercentage = BigDecimal.valueOf(0.10); // 10%
//        return transferAmount.multiply(feePercentage);
//    }
}
