package com.weblabs.lab3server.module.repo

import com.weblabs.lab3server.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface MessageRepository : JpaRepository<Message, Long>