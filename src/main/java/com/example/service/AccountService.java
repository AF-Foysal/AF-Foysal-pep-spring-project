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


    public void register(Account account) throws RequirementNotMetException, ConflictException {
        if (accountRepository.findByUsername(account.getUsername()) != null){
            throw new ConflictException();
        }
        if ( (account.getUsername().isEmpty()) || (account.getPassword().length() < 4) ){
            throw new RequirementNotMetException();
        }
        accountRepository.save(account);
    }

    public void login(Account account) throws AuthenticationException { 
        if (accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()) == null){
            throw new AuthenticationException();
        }
    }



}
