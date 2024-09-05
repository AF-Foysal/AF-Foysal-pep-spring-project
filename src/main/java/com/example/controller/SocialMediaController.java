package com.example.controller;


import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.exception.ConflictException;
import com.example.exception.RequirementNotMetException;
import com.example.service.AccountService;
import com.example.service.MessageService;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


@RestController
public class SocialMediaController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account account) throws RequirementNotMetException, ConflictException {
        accountService.register(account);
        return ResponseEntity.status(HttpStatus.OK).build();
    } 

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account) throws AuthenticationException {
        accountService.login(account);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @ExceptionHandler(RequirementNotMetException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerCredentials(RequirementNotMetException e){return "Requirements not met. "; }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handlerConflict(ConflictException e){return "Username already in use."; }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handlerUnauthorized(AuthenticationException e){return "Unauthorized login."; }

}
