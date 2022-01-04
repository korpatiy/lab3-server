package com.weblabs.lab3server.rest.controller

import com.weblabs.lab3server.model.Message
import com.weblabs.lab3server.module.service.MessageService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView
import javax.validation.Valid

@Controller
@RequestMapping("/templates")
class MessageTemplateController(
    private val messageService: MessageService
) {

    @GetMapping
    fun getIndex(message: Message, model: Model): String {
        model.addAttribute("messages", messageService.getAll().sortedByDescending { it.clap })
        return "index"
    }

    @PostMapping
    fun postMessage(
        redirectAttributes: RedirectAttributes,
        @ModelAttribute @Valid message: Message,
        bindingResult: BindingResult,
        model: Model
    ): RedirectView {
        val redirectView = RedirectView("/templates", true)
        if (!bindingResult.hasErrors()) {
            messageService.save(message)
            redirectAttributes.addFlashAttribute("isSent", true)
        } else {
            val fieldErrors = bindingResult.fieldErrors.map { it.defaultMessage }
            redirectAttributes.addFlashAttribute("errors", fieldErrors)
        }
        return redirectView
    }

    @GetMapping("/messages/{message_id}")
    fun getMessage(@PathVariable message_id: Long, model: Model): String {
        model.addAttribute(
            "message", messageService.findById(message_id)
        )
        return "message"
    }

    @PostMapping("/clap/{message_id}")
    fun clapMessage(@PathVariable message_id: Long): String {
        messageService.clapMessage(message_id)
        return "redirect:/templates"
    }
}