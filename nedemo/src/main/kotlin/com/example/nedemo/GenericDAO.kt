package com.example.nedemo

import org.springframework.data.jpa.repository.JpaRepository

class GenericDAO<T : Any, ID : Any>(private val repository: JpaRepository<T, ID>){

    fun getAll(): List<T> {
        return repository.findAll()
    }
    fun findById(id: ID): T? {
        return repository.findById(id).orElseThrow { IllegalArgumentException("Invalid customer Id:$id") }
    }
    fun add(entity: T): T {
        return repository.save(entity)
    }
    fun update(entity: T): T {
        return repository.save(entity)
    }
    fun deleteById(id: ID) {
        return repository.deleteById(id)
    }
}