package com.example.nedemo.models

import jakarta.persistence.*
import jakarta.validation.constraints.Positive


@Entity
@Table(name = "Role_")
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Role", nullable = false)
    @Positive
    val id: Long = 0,

    @Column(name = "Name_Role", nullable = false, length = 1024)
    val nameRole: String = "",
)