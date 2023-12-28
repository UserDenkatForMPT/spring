package com.example.nedemo.repositories

import com.example.nedemo.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long>{
    fun findByLogin(@Param("login") login: String?):User?
    fun findById(@Param("idUser") idUser: Int):User?
    fun deleteById(@Param("idUser") idUser: Int)
}