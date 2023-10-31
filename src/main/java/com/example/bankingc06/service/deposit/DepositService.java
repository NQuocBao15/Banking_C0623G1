package com.example.bankingc06.service.deposit;

import com.example.bankingc06.model.Deposit;
import com.example.bankingc06.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DepositService implements IDepositService{
    @Autowired
    private DepositRepository depositRepository;

    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public Deposit findById(Long id) {
        return null;
    }

    @Override
    public void create(Deposit deposit) {
        depositRepository.save(deposit);
    }

    @Override
    public void update(Long id, Deposit deposit) {

    }

    @Override
    public void removeById(Long id) {

    }
}
