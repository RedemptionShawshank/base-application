package com.example.SpringSecurity.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetResponse {

    private boolean success;
    private String status;
    private Company company;
}
