package com.weblabs.lab3server.module.service

import com.weblabs.lab3server.model.Message
import com.weblabs.lab3server.module.repo.MessageRepository
import com.weblabs.lab3server.rest.dto.ClapsCount
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageService(
    private val repository: MessageRepository
) {

    fun getAll(): List<Message> = repository.findAll()

    fun save(message: Message) = repository.save(message)

    fun findById(id: Long): Message = repository.findById(id).orElseThrow {
        throw NoSuchElementException("Сообщение с идентификатором $id не найдено")
    }

    @Transactional
    fun clapMessage(id: Long): ClapsCount {
        val message = findById(id)
        message.clap += 1
        save(message)
        return ClapsCount(message.clap)
    }
}