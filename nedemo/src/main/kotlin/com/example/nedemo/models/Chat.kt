package com.example.nedemo.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Positive
import java.time.LocalDate

@Entity
@Table(name = "Chat")
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Chat", nullable = false)
    @Positive
    val id: Long = 0,

    @Column(name = "Name_Chat", nullable = false, length = 10240)
    val nameChat: String = "",

    @Column(name = "Update_Time_Chat", nullable = false, length = 10240)
    var updateTimeChat: String = LocalDate.now().toString(),

    @ManyToOne
    @JoinColumn(name = "User_ID")
    var user: User = User(),

    @ManyToOne
    @JoinColumn(name = "Model_ID")
    val model: Model = Model(),

    @JsonIgnore
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "MKM_Chat_Module",
        joinColumns = [JoinColumn(name = "Chat_ID")],
        inverseJoinColumns = [JoinColumn(name = "Module_ID")]
    )
    var modules: MutableList<Module> = mutableListOf(),
)