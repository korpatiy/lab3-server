package com.weblabs.lab3server.model

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "t_message")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    var id: Long = 0,

    @Column(name = "author", length = 30)
    //@field:NotEmpty(message = "Name should not be empty")
    @field:Size(min = 1, max = 30, message = "Поле 'Имя отправителя' должно содержать хотя бы 1 символ и не превышать 30")
    var author: String = "",

    @Column(name = "text", length = 1000)
    //@field:NotEmpty(message = "Year should not be empty")
    @field:Size(min = 1, max = 1000, message = "Поле 'Сообщение' должно содержать хотя бы 1 символ и не превышать 1000")
    var text: String = "",

    @Column(name = "clap")
    var clap: Int = 0
)