package com.example.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.MTException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) throws MTException {
        if ( (message.getMessageText().isEmpty()) || (message.getMessageText().length() > 255 )){
            throw new MTException();
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageByID(Integer messageId){
        return messageRepository.findById(messageId).orElse(null);
    }

    public int deleteByMessageID(Integer messageId){
        return messageRepository.deleteByMessageID(messageId);
    }

    public List<Message> getAllMessagesByPostedBy(Integer postedBy){
        return messageRepository.findAllByPostedBy(postedBy);
    }

    public Message updateMessage(Integer messageId, String messageText) throws MTException, ResourceNotFoundException {
        if ( (messageText.isEmpty()) || (messageText.length() > 255 )){
            throw new MTException();
        }
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new ResourceNotFoundException() );
        message.setMessageText(messageText);
        return messageRepository.save(message);
    }


}
