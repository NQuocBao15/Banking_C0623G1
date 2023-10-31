package com.example.bankingc06.controller;

import com.example.bankingc06.model.Customer;
import com.example.bankingc06.model.Deposit;
import com.example.bankingc06.model.Transfer;
import com.example.bankingc06.model.Withdraw;
import com.example.bankingc06.service.customer.CustomerService;
import com.example.bankingc06.service.customer.ICustomerService;
import com.example.bankingc06.service.deposit.IDepositService;
import com.example.bankingc06.service.transfer.ITransferService;
import com.example.bankingc06.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITransferService transferService;
    @Autowired
    private IDepositService depositService;
    @Autowired
    private IWithdrawService withdrawService;

    @GetMapping
    public String showListPage(Model model) {
        List<Customer> customers = customerService.findAllByDeleted(false);
        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());

        return "customer/create";
    }

    @GetMapping("/edit/{customerId}")
    public String showEditPage(@PathVariable Long customerId, Model model) {
        model.addAttribute("customer", customerService.findById(customerId));

        return "customer/edit";
    }

    @GetMapping("/delete/{customerId}")
    public String delete(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        return "customer/delete";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        Deposit deposit = new Deposit();
        deposit.setCustomer(customer);

        model.addAttribute("deposit", deposit);
        return "customer/deposit";
    }

    @GetMapping("withdraw/{customerId}")
    public String showWithdrawPage(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customer);

        model.addAttribute("withdraw", withdraw);
        return "customer/withdraw";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {

        Customer sender = customerService.findById(senderId);
        List<Customer> recipient = customerService.findAllWithoutId(senderId, false);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);

        model.addAttribute("transfer", transfer);
        model.addAttribute("recipients", recipient);

        return "customer/transfer";
    }

    @GetMapping("/history-transfer")
    public String showHistoryTransferPage(Model model) {
        List<Transfer> transfers = transferService.findAll();
        model.addAttribute("transfers", transfers);

        return "customer/history-transfer";
    }

    @GetMapping("/history-deposit")
    public String showHistoryDepositPage(Model model) {
        List<Deposit> deposits = depositService.findAll();
        model.addAttribute("deposits", deposits);

        return "customer/history-deposit";
    }

    @GetMapping("/history-withdraw")
    public String showHistoryWithdrawPage(Model model) {
        List<Withdraw> withdraws = withdrawService.findAll();
        model.addAttribute("withdraws", withdraws);

        return "customer/history-withdraw";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute Customer customer, Model model) {
        if (customer.getFullName().isEmpty()) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Create Unsuccessful");
        } else {
            customerService.create(customer);

            model.addAttribute("customer", new Customer());
            model.addAttribute("success", true);
            model.addAttribute("message", "Create Successful");
        }

        return "customer/create";
    }

    @PostMapping("/edit/{customerId}")
    public String editCustomer(@PathVariable Long customerId, @ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        customerService.update(customerId, customer);

        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Update successfully");

        return "redirect:/customers";
    }

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId, RedirectAttributes redirectAttributes) {

        customerService.removeById(customerId);

        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");

        return "redirect:/customers";
    }

    @PostMapping("/deposit/{customerId}")
    public String deposit(@PathVariable Long customerId, @ModelAttribute Deposit deposit, Model model) {

        Customer customer = customerService.findById(customerId);

        if (deposit.getTransactionAmount().compareTo(BigDecimal.ZERO) > 0 && !customer.getDeleted()) {
            deposit.setCustomer(customer);

            model.addAttribute("deposit", deposit);
            model.addAttribute("success", true);
            model.addAttribute("message", "Deposit successfully");
            customerService.deposit(deposit);
        } else {

            model.addAttribute("deposit", deposit);
            model.addAttribute("success", false);
            model.addAttribute("message", "Deposit unsuccessfully");
        }

        deposit.setTransactionAmount(BigDecimal.ZERO);

        return "customer/deposit";
    }

    @PostMapping("/withdraw/{customerId}")
    public String withdraw(@PathVariable Long customerId, @ModelAttribute Withdraw withdraw, Model model) {
        Customer customer = customerService.findById(customerId);

        if (customerService.findById(customerId).getBalance().compareTo(withdraw.getAmount()) >= 0 && withdraw.getAmount().compareTo(BigDecimal.ZERO) > 0 && !customer.getDeleted()) {
            withdraw.setCustomer(customer);

            model.addAttribute("withdraw", withdraw);
            model.addAttribute("success", true);
            model.addAttribute("message", "Withdraw successfully");
            customerService.withdraw(withdraw);

        } else {
            model.addAttribute("withdraw", withdraw);
            model.addAttribute("success", false);
            model.addAttribute("message", "Withdraw unsuccessfully");
        }

        withdraw.setAmount(null);

        return "customer/withdraw";
    }

    @PostMapping("/transfer/{senderId}")
    public String transfer(@PathVariable Long senderId, @RequestParam Long recipientId, @ModelAttribute Transfer transfer, Model model, @RequestParam BigDecimal transactionAmount) {

        Customer sender = customerService.findById(senderId);
        Customer recipient = customerService.findById(recipientId);

        transfer.setSender(sender);
        transfer.setRecipient(recipient);

        if (customerService.findById(senderId).getBalance().compareTo(transactionAmount) >= 0 && transactionAmount.compareTo(BigDecimal.ZERO) > 0 && !transfer.getSender().getDeleted()) {
            transfer.setTransactionAmount(transactionAmount);
            customerService.processTransfer(transfer);
            model.addAttribute("success", true);
            model.addAttribute("message", "Transfer successfully");

        } else {

            model.addAttribute("success", false);
            model.addAttribute("message", "Transfer unsuccessfully");

        }

        List<Customer> recipients = customerService.findAllWithoutId(senderId, false);
        model.addAttribute("recipients", recipients);
        return "customer/transfer";
    }
}
