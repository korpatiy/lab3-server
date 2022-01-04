package com.weblabs.lab3server.rest.controller

import com.weblabs.lab3server.model.Message
import com.weblabs.lab3server.module.service.MessageService
import com.weblabs.lab3server.rest.dto.ClapsCount
import com.weblabs.lab3server.rest.dto.NewMessageDTO
import com.weblabs.lab3server.rest.dto.ResponseError
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/messages")
@Tag(
    name = "Messages",
    description = "Сообщения Мини-Почты"
)
class MessageController(
    private val messageService: MessageService
) {

    @GetMapping
    @Operation(description = "Возвращает список всех сообщений, отсортированных по убыванию по количеству хлопков")
    fun getMessages() = ResponseEntity.ok().body(messageService.getAll().sortedByDescending { it.clap })

    @GetMapping("/{message_id}")
    @Operation(description = "Возвращает сообщение по ID")
    fun getMessage(@PathVariable message_id: Long): ResponseEntity<Any> {
        return try {
            val message = messageService.findById(message_id)
            ResponseEntity.ok().body(message)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.message?.let { ResponseError(it) })
        }
    }

    @PostMapping
    @Operation(description = "Создает новое сообщение")
    fun createMessage(
        @Valid @RequestBody messageDTO: NewMessageDTO,
        bindingResult: BindingResult
    ): ResponseEntity<Any> {
        return if (bindingResult.hasErrors()) {
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(bindingResult.fieldErrors.map { it.defaultMessage?.let { message -> ResponseError(message) } })
        } else
            ResponseEntity.ok()
                .body(messageService.save(Message(author = messageDTO.author, text = messageDTO.text)))
    }

    @PostMapping("/{message_id}/clap")
    @Operation(description = "Увеличивает количество хлопков сообщения на 1")
    fun clapMessage(@PathVariable message_id: Long): ResponseEntity<Any> {
        return try {
            val clapsCount = messageService.clapMessage(message_id)
            ResponseEntity.ok().body(clapsCount)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.message?.let { ResponseError(it) })
        }
    }
}