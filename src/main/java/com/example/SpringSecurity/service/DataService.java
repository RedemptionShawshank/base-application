package com.example.SpringSecurity.service;


import com.example.SpringSecurity.entity.Company;
import com.example.SpringSecurity.entity.OutboxTable;
import com.example.SpringSecurity.entity.TimeLogs;
import com.example.SpringSecurity.repository.CompanyRepository;
import com.example.SpringSecurity.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class DataService {

    private final CompanyRepository companyRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String,String>redisTemplate;
    private final TimeLogs timeLogs;


    // transactional method should always be private
     // call transactional method from another class else if you call it from same class it will bypass proxy and will not start a transaction
     // if try catch is there in transactional method, rollback will not work

    @Transactional
    public void databaseOperations(Company input){

        //add in my db table
        companyRepository.save(input);

        // add in cache
        String key = "company:" + input.getId();
        StringBuilder value = new StringBuilder();
        try{
            // convert object to json String
            value.append(objectMapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        redisTemplate.opsForValue().set(key, value.toString(), 10, TimeUnit.MINUTES);

        //add in outbox table
        OutboxTable message = new OutboxTable();
        message.setMessage(value.toString());
        message.setProcessed(false);
        message.setCreatedAt(LocalDateTime.now());
        // if other operations
        outboxRepository.save(message);
        // how to store the exact time at which we store in out db

    }
}
