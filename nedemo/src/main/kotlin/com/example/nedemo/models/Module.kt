package com.example.nedemo.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "Module")
data class Module(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Module", nullable = false)
    @Positive
    val id: Long = 0,

    @Column(name = "Name_Module", nullable = false, length = 10240)
    val nameModule: String = "",

    @Column(name = "Desc_Module", nullable = false, length = 10240)
    val descModule: String = "",

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "MKM_Chat_Module",
        joinColumns = [JoinColumn(name = "Module_ID")],
        inverseJoinColumns = [JoinColumn(name = "Chat_ID")]
    )
    var chats: MutableList<Chat> = mutableListOf(),
)