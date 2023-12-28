package com.example.nedemo.repositories

import com.example.nedemo.models.Chat
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<Chat, Long>{
}