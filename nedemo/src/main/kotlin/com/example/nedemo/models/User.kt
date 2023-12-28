package com.example.nedemo.models

import jakarta.persistence.*
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

@Entity
@Table(name = "User_")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_User", nullable = false)
    @Positive
    val id: Long = 0,

    @Column(name = "login", nullable = false)
    @Size(min = 3, max = 200)
    val login: String = "",

    @Column(name = "password", nullable = false)
    @Size(min = 3, max = 200)
    var password: String = "",

    @ManyToOne
    @JoinColumn(name = "Role_ID")
    var role: Role = Role(),

    @ManyToOne
    @JoinColumn(name = "tarif_ID")
    var tarif: Tarif = Tarif()
)