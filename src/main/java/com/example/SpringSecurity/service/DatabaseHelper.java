package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class DatabaseHelper {
    private final DataService dataService;

//    @Synchronized
//    public boolean addInDB(Company input){
//        try{
//
//            dataService.databaseOperations(input);
//
//        }catch(DataAccessException ex){
//            log.warn(ex.getMessage());
//            return false;
//        }
//        return true;
//    }
}
