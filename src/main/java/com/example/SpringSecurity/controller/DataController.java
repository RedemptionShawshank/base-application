package com.example.SpringSecurity.controller;


import com.example.SpringSecurity.entity.Company;
import com.example.SpringSecurity.entity.GetResponse;
import com.example.SpringSecurity.entity.Response;
import com.example.SpringSecurity.exceptions.NotFoundException;
import com.example.SpringSecurity.service.CacheService;
import com.example.SpringSecurity.service.DatabaseHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/data")
@AllArgsConstructor
public class DataController {

    private final DatabaseHelper databaseHelper;
    private final CacheService cacheService;

    @PostMapping(path = "/user_info1")
    public ResponseEntity<Response> addUserInfo(@RequestBody Company request){
        Response response = new Response();
        if(databaseHelper.addInDB(request)){
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
        response.setSuccess(false);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/user_info2")
    public ResponseEntity<Response> addUserInfo2(@RequestBody Company request){
        Response response = new Response();
        if(cacheService.saveCompany(request)!=null){
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
        response.setSuccess(false);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get_info1")
    public ResponseEntity<GetResponse> getUserInfo(@RequestParam("id") Long id){

        GetResponse response = new GetResponse();


        Company company = null;
        try {
            company = databaseHelper.getUserInfo(id);
        }catch (NotFoundException ex){
            response.setSuccess(false);
            response.setStatus(ex.getMessage());
            response.setCompany(new Company());
            return ResponseEntity.ok(response);
        }

        response.setSuccess(true);
        response.setStatus("Found");
        response.setCompany(company);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get_info2")
    public ResponseEntity<GetResponse> getUserInfo2(@RequestParam("id") Long id){

        GetResponse response = new GetResponse();


        Company company = null;
        try {
            company = cacheService.getCompany(id);
        }catch (NotFoundException ex){
            response.setSuccess(false);
            response.setStatus(ex.getMessage());
            response.setCompany(new Company());
            return ResponseEntity.ok(response);
        }

        response.setSuccess(true);
        response.setStatus("Found");
        response.setCompany(company);
        return ResponseEntity.ok(response);
    }
}
