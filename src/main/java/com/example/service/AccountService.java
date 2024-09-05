package com.example.service;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ConflictException;
import com.example.exception.RequirementNotMetException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public Account register(Account account) throws RequirementNotMetException, ConflictException {
        if (accountRepository.findByUsername(account.getUsername()) != null){
            throw new ConflictException();
        }
        if ( (account.getUsername().isEmpty()) || (account.getPassword().length() < 4) ){
            throw new RequirementNotMetException();
        }
        return accountRepository.save(account);
    }

    public void login(Account account) throws AuthenticationException { 
        if (accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()) == null){
            throw new AuthenticationException();
        }
    }


    /*
     * Utility Functions
     */

    public Account findAccount(Account account){
        return accountRepository.findByUsername(account.getUsername());
    }

    public boolean existsByID(Integer account_id){
        return accountRepository.existsById(account_id);
    }



}
