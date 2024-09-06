package com.example.controller;


import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountNotFoundException;
import com.example.exception.ConflictException;
import com.example.exception.MTException;
import com.example.exception.RequirementNotMetException;
import com.example.exception.ResourceNotFoundException;
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
    public ResponseEntity<Account> register(@RequestBody Account account) throws RequirementNotMetException, ConflictException {
        Account successfulAccount = accountService.register(account);
        return ResponseEntity.status(HttpStatus.OK).body(successfulAccount);
    } 

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        accountService.login(account);
        Account successfulAccount = accountService.findAccount(account);
        return ResponseEntity.status(HttpStatus.OK).body(successfulAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) throws MTException, AccountNotFoundException {
        if (!accountService.existsByID(message.getPostedBy())){
            throw new AccountNotFoundException();
        }
        Message savedMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(savedMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageByID(@PathVariable Integer message_id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageByID(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageByID(@PathVariable Integer message_id){
        if (messageService.deleteByMessageID(message_id) == 1){
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
       
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByPostedBy(@PathVariable Integer account_id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessagesByPostedBy(account_id));
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer message_id, @RequestBody Message message) throws MTException, ResourceNotFoundException{
        String messageText = message.getMessageText();
        messageService.updateMessage(message_id, messageText);
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }
    

    @ExceptionHandler(RequirementNotMetException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerCredentials(RequirementNotMetException e){return "Requirements not met. "; }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handlerConflict(ConflictException e){return "Username already in use. "; }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handlerUnauthorized(AuthenticationException e){return "Unauthorized login. "; }

    @ExceptionHandler(MTException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMessageError(MTException e){return "Message must NOT be over 255 characters NOR blank. "; }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMessageError(AccountNotFoundException e){return "Account does NOT exist. "; }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleResourceNonexistent(ResourceNotFoundException e){return "Message does NOT exist. "; }

}
