package com.nomlybackend.nomlybackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class CreateDatabaseEntry {
    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    CreateDatabaseEntry(){ setCreatedAt(); }

    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }
}
