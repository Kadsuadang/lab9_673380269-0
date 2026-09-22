package com.example.lab9.service;

import com.example.lab9.model.Account;
import com.example.lab9.model.DepositTransaction;
import com.example.lab9.repository.AccountRepository;
import com.example.lab9.repository.DepositRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositService {

    private final AccountRepository accountRepository;
    private final DepositRepository depositRepository;

    public DepositService(AccountRepository accountRepository, DepositRepository depositRepository) {
        this.accountRepository = accountRepository;
        this.depositRepository = depositRepository;
    }


    public void deposit(Long accountId, Double amount) {

        // 1. ค้นหา Account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 2. เพิ่ม balance แล้วบันทึก
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // 3. สร้าง DepositTransaction ผูกกับ Account
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setAccount(account);
        depositRepository.save(transaction);

        // ทดลอง rollback (ข้อ 12) ให้เปิดบรรทัดนี้:
        throw new RuntimeException("Test Rollback");
    }
}