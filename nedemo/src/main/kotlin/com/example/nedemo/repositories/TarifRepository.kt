package com.example.nedemo.repositories

import com.example.nedemo.models.Tarif
import org.springframework.data.jpa.repository.JpaRepository

interface TarifRepository : JpaRepository<Tarif, Long>{
}