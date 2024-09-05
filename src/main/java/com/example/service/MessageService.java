package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Message;
import com.example.exception.MTException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    /**
     * Story 3: Message Creation
     * MessageText Length must NOT be Blank nor Greater than 255
     * @param message
     */
    public void createMessage(Message message) throws MTException{
        if ((message.getMessageText().isEmpty()) && (message.getMessageText().length() > 255)){
            throw new MTException();
        }
        messageRepository.save(message);
    }

    /**
     * Story 4: Retrieve All Messages
     * @return List of all Messages
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    
    /**
     * Story 5: Retrieve Message by ID
     * @param message_id
     * @return an Optional<Message>
     */
    public Optional<Message> getMessageByID(int message_id){
        return messageRepository.findById(message_id);
    }

    /**
     * Story 6: Delete Message By ID
     * @param message_id
     */
    public void deleteMessageByID(int message_id){
        messageRepository.deleteById(message_id);
    }

    /**
     * Story 7: Update Message Text
     * @param message_id
     * @param message_text
     */
    public void updateMessageText(int message_id, String message_text) throws MTException{
        if (!messageRepository.existsById(message_id)  || (message_text.isEmpty()) || (message_text.length() > 255)){
            throw new MTException();
        }
        Message message = messageRepository.getById(message_id);
        message.setMessageText(message_text);
        messageRepository.save(message);
    }

    /**
     * Story 8: Retrieve all Messages by Account ID
     * @param account_id
     * @return List of all Messages
     */
    public List<Message> getAllMessagesByAccountID(int account_id){
        return messageRepository.findAllByAccountID(account_id);
    }

}
