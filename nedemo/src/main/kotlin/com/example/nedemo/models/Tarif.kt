package com.example.nedemo.models

import jakarta.persistence.*
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "Tarif")
data class Tarif (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Tarif", nullable = false)
    @Positive
    val id: Long = 0,

    @Column(name = "Name_Tarif", nullable = false, length = 10240)
    val nameTarif: String = "",

    @Column(name = "Desc_Tarif", nullable = false, length = 10240)
    val descTarif: String = "",

    @Column(name = "Price_Tarif", nullable = false, length = 10240)
    val priceTarif: String = "",
)