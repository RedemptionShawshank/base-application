package com.example.SpringSecurity.service;


import com.example.SpringSecurity.entity.Company;
import com.example.SpringSecurity.exceptions.NotFoundException;
import com.example.SpringSecurity.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheService {

    private final CompanyRepository companyRepository;

    // Always update DB + refresh cache
    // caches whatever our method returns
    @CachePut(value = "companies", key = "#result.id")
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }


    // Cache lookup → if miss → call DB, cache it, return
    @Cacheable(value = "companies", key = "#id")
    public Company getCompany(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found"));
    }


}
