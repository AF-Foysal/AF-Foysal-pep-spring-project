package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.RequirementNotMetException;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


@RestController
public class SocialMediaController {

    private MessageService messageService;
    private AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService MessageService, AccountService AccountService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Story 1: Account Registration
     * 200: Account is registered successfully
     * 409: Conflict
     * 400: Client Error
     * @param requestBody
     * @return ResponseEntity 
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @PostMapping("/register")
    public ResponseEntity registerAccount(@RequestBody String requestBody) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(requestBody, Account.class);
        try{
            accountService.register(account);
        }
        catch(AccountAlreadyExistsException e){
            return ResponseEntity.status(409).body("");
        }
        catch(RequirementNotMetException e){
            return ResponseEntity.status(400).body("");
        }
        return ResponseEntity.status(200).body(account);

    }







}
