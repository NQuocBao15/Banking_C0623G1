package com.example.bankingc06.service.transfer;

import com.example.bankingc06.model.Transfer;
import com.example.bankingc06.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransferService implements ITransferService{
    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer findById(Long id) {
        return null;
    }

    @Override
    public void create(Transfer transfer) {

    }

    @Override
    public void update(Long id, Transfer transfer) {

    }

    @Override
    public void removeById(Long id) {

    }
}
