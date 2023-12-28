package com.example.nedemo.models

import jakarta.persistence.*
import jakarta.validation.constraints.Positive
import java.time.LocalDate

@Entity
@Table(name = "Model")
data class Model(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Model", nullable = false)
    @Positive
    val id: Long = 0,

    @Column(name = "Name_Model", nullable = false, length = 10240)
    val nameModel: String = "",

    @Column(name = "Desc_Model", nullable = false, length = 10240)
    val descModel: String = "",

    @Column(name = "Creation_Time_Model", nullable = false, length = 10240)
    val creationTime: String = LocalDate.now().toString(),
)