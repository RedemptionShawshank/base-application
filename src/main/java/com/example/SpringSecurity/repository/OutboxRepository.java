package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.OutboxTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OutboxTable,Integer> {

}
