package com.weblabs.lab3server.rest.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Сообщение ошибки от сервера")
data class ResponseError(
    @Schema(description = "Сообщение ошибки", example = "Автор не должен быть длиннее 30 символов")
    val message: String = ""
)