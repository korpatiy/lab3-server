package com.weblabs.lab3server.module.service

import com.weblabs.lab3server.model.Message
import com.weblabs.lab3server.module.repo.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val repository: MessageRepository
) {

    fun getAll(): List<Message> = repository.findAll()

    fun save(message: Message) = repository.save(message)

    fun getById(id: Long) = repository.getById(id)

    fun clapMessage(id: Long) = repository.clapMessage(id)
}