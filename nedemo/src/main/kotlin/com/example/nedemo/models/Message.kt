package com.example.nedemo.models

import jakarta.persistence.*
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "Message")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Message", nullable = false)
    @Positive
    val id: Long = 0,

    @Column(name = "Text_Message", nullable = false, length = 10240)
    val textMessage: String = "",

    @Column(name = "Number_Message", nullable = false)
    @Positive
    var numberMessage: Long = 0,

    @Column(name = "Is_User_Message", nullable = false)
    var isUserMessage: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "Chat_ID")
    val chat: Chat = Chat(),
){
    fun getShortenedTextMessage(): String {
        return if (this.textMessage.length > 35) {
            this.textMessage.substring(0, 35) + "..."
        } else {
            this.textMessage
        }
    }
}