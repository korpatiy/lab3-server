package com.weblabs.lab3server.rest.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Size

@Schema(description = "DTO для создания сообщений")
data class NewMessageDTO(
    @field:Size(
        min = 1,
        max = 30,
        message = "Поле 'Имя отправителя' должно содержать хотя бы 1 символ и не превышать 30"
    )
    @Schema(description = "Имя автора сообщения", example = "Sender Name")
    val author: String = "",
    @field:Size(
        min = 1,
        max = 1000,
        message = "Поле 'Сообщение' должно содержать хотя бы 1 символ и не превышать 1000")
    @Schema(description = "Текст сообщения", example = "Lorem ipsum...")
    val text: String = ""
)