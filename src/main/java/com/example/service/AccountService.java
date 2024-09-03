package com.example.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Story 1: Account Registration
     * @param account
     */
    public void register(Account account){
        if ((!account.getUsername().isEmpty()) && (account.getPassword().length() >= 4)){
            accountRepository.save(account);
        }
    }

    /**
     * Story 2: Account Login
     * @param account
     * @throws AuthenticationException Account is not found or Password incorrect
     */
    public void login(Account account) throws AuthenticationException {
        accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }


}
