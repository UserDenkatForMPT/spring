package com.example.nedemo.repositories

import com.example.nedemo.models.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long>{
}