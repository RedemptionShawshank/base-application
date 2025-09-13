package com.example.SpringSecurity.service;


import com.example.SpringSecurity.entity.Company;
import com.example.SpringSecurity.entity.OutboxTable;
import com.example.SpringSecurity.repository.CompanyRepository;
import com.example.SpringSecurity.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DataService {

    private final CompanyRepository companyRepository;
    private final OutboxRepository outboxRepository;

    public DataService(CompanyRepository companyRepository, OutboxRepository outboxRepository) {
        this.companyRepository = companyRepository;
        this.outboxRepository = outboxRepository;
    }
    // transactional method should always be private
     // call transactional method from another class else if you call it from same class it will bypass proxy and will not start a transaction
     // if try catch is there in transactional method, rollback will not work

    @Transactional
    public void databaseOperations(Company input){

        //add in my db table
        companyRepository.save(input);

        //add in outbox table
        OutboxTable message = new OutboxTable();
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder jsonMessage=new StringBuilder();
        try {
            jsonMessage.append(objectMapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        message.setMessage(jsonMessage.toString());
        message.setProcessed(false);
        message.setCreatedAt(LocalDateTime.now());
        // if other operations
        outboxRepository.save(message);
        // how to store the exact time at which we store in out db

    }
}
