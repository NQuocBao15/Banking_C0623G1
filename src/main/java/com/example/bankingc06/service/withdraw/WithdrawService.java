package com.example.bankingc06.service.withdraw;

import com.example.bankingc06.model.Withdraw;
import com.example.bankingc06.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class WithdrawService implements IWithdrawService{
    @Autowired
    private WithdrawRepository withdrawRepository;

    @Override
    public List<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public Withdraw findById(Long id) {
        return null;
    }

    @Override
    public void create(Withdraw withdraw) {

    }

    @Override
    public void update(Long id, Withdraw withdraw) {

    }

    @Override
    public void removeById(Long id) {

    }
}
