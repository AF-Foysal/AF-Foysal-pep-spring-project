package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{


    @Transactional
    @Modifying
    @Query("DELETE FROM Message WHERE messageId = ?1")
    int deleteByMessageID(Integer messageId);

    List<Message> findAllByPostedBy(Integer postedBy);
}
