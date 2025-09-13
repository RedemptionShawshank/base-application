package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.UserAuthEntity;
import com.example.SpringSecurity.repository.UserAuthEntityRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthEntityService implements UserDetailsService {

    private UserAuthEntityRepository userAuthEntityRepository;

    public UserAuthEntityService(UserAuthEntityRepository userAuthEntityRepository){
        this.userAuthEntityRepository = userAuthEntityRepository;
    }

    @Override
    public UserAuthEntity loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAuthEntity user = userAuthEntityRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        return user;
        // returning in this format because we have used Optional as the return type
    }

    public void save(UserAuthEntity user){
        userAuthEntityRepository.save(user);
    }
}
