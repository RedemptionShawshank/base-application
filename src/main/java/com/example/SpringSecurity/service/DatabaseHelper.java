package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.Company;
import com.example.SpringSecurity.entity.TimeLogs;
import com.example.SpringSecurity.exceptions.NotFoundException;
import com.example.SpringSecurity.repository.CompanyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
@Service
public class DatabaseHelper {
    private final DataService dataService;
    private final CompanyRepository companyRepository;
    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final TimeLogs timeLogs;

    @Synchronized
    public boolean addInDB(Company input){
        try{
            dataService.databaseOperations(input);
        }catch(DataAccessException ex){
            log.warn(ex.getMessage());
            return false;
        }
        return true;
    }

    public Company getUserInfo(Long id){
        //Optional<Company> user = companyRepository.findByid();

//        if (user.isPresent()) {
//            return user.get();
//        } else {
//            System.out.println("No company found with id: " + id);
//        }

        timeLogs.setStartTime(System.currentTimeMillis());
        // form the key
        String key = "company:"+id;

        // fetch from cache first
        String cachevalue = redisTemplate.opsForValue().get(key);

        // convert from json string to java object
        if(cachevalue!=null){
            try {
                Company company = objectMapper.readValue(cachevalue,Company.class);// return the response: cache hit
                log.info("Cache hit");
                timeLogs.setEndtime(System.currentTimeMillis());
                log.info("Time take when cache hit {}",timeLogs.getEndtime()- timeLogs.getStartTime());
                return company;
            } catch (JsonProcessingException e) {
                log.warn(e.getMessage());
                throw new NotFoundException("Not Found");
            }
        }

        // cache miss
        log.info("Cache miss");
        log.info("Fetching from DB");

        // fetch from db
        Company user = companyRepository.findById(id)
                .orElseThrow(() ->new NotFoundException("Not Found"));

        //update cache

        StringBuilder value = new StringBuilder();
        try {
            value.append(objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage());
            throw new NotFoundException("Not Found");
        }
        redisTemplate.opsForValue().set(key, value.toString(), 10, TimeUnit.MINUTES);
        log.info("Cache updated");

        timeLogs.setEndtime(System.currentTimeMillis());
        log.info("Time take when cache miss {}",timeLogs.getEndtime()- timeLogs.getStartTime());

        return user;
    }
}
