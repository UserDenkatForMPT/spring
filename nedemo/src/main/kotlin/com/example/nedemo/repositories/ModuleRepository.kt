package com.example.nedemo.repositories

import com.example.nedemo.models.Module
import org.springframework.data.jpa.repository.JpaRepository

interface ModuleRepository : JpaRepository<Module, Long>{
}