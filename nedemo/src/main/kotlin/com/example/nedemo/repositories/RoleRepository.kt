package com.example.nedemo.repositories

import com.example.nedemo.models.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>{
}