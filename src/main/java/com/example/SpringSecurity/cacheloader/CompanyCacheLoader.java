package com.example.SpringSecurity.cacheloader;

import com.example.SpringSecurity.entity.Company;
import com.example.SpringSecurity.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;

@AllArgsConstructor
@Slf4j
public class CompanyCacheLoader implements CacheLoaderWriter<Long, Company> {
    private final CompanyRepository companyRepository;


    @Override
    public Company load(Long key) throws Exception {
        return companyRepository.findByid(key).orElse(null);
    }

    @Override
    public void write(Long key, Company company) throws Exception {

    }

    @Override
    public void delete(Long aLong) throws Exception {

    }
}
