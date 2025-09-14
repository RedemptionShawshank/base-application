package com.example.SpringSecurity.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Outbox_Table")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutboxTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "Message",columnDefinition = "json")
    private String message;

    @Column(name = "Processed")
    private boolean isProcessed;

    @Column(name = "Creation_Time")
    private LocalDateTime createdAt;
}
