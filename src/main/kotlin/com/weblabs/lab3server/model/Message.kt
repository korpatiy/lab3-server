package com.weblabs.lab3server.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "t_message")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    @Schema(description = "Идентификатор сообщения", example = "1")
    var id: Long = 0,

    @Column(name = "author", length = 30)
    @field:Size(
        min = 1,
        max = 30,
        message = "Поле 'Имя отправителя' должно содержать хотя бы 1 символ и не превышать 30"
    )
    @Schema(description = "Имя автора сообщения", example = "Sender Name")
    var author: String = "",

    @Column(name = "text", length = 1000)
    @Schema(description = "Текст сообщения", example = "Lorem ipsum...")
    @field:Size(min = 1, max = 1000, message = "Поле 'Сообщение' должно содержать хотя бы 1 символ и не превышать 1000")
    var text: String = "",

    @Column(name = "clap")
    @Schema(description = "Количество хлопков", example = "42")
    var clap: Int = 0
)