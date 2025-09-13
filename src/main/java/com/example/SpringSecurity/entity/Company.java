package com.example.SpringSecurity.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Company")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "Name")
    private String employeeName;

    @Column(name = "Age")
    private int age;

    @Column(name = "Employer")
    private String employer;

    @Column(name = "Designation")
    private String designation;

}
