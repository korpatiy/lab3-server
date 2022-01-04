package com.weblabs.lab3server.rest.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Отображает количество хлопков")
data class ClapsCount(
    @Schema(description = "Количество хлопков", example = "42")
    val count: Int = 0
)