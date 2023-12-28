package com.example.nedemo.repositories

import com.example.nedemo.models.Model
import org.springframework.data.jpa.repository.JpaRepository

interface ModelRepository : JpaRepository<Model, Long>{
}