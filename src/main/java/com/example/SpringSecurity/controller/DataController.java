package com.example.SpringSecurity.controller;


import com.example.SpringSecurity.entity.Company;
import com.example.SpringSecurity.entity.Response;
import com.example.SpringSecurity.service.DataService;
import com.example.SpringSecurity.service.DatabaseHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/data")
@AllArgsConstructor
public class DataController {

    private final DatabaseHelper databaseHelper;
    private final DataService dataService;


    @PostMapping(path = "/user_info")
    public ResponseEntity<Response> addUserInfo(@RequestBody Company request){
        Response response = new Response();
        if(dataService.addInDB(request)){
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
        response.setSuccess(false);
        return ResponseEntity.ok(response);
    }
}
