package com.example.nedemo.controllers.admin


import com.example.nedemo.GenericDAO
import com.example.nedemo.models.Chat
import com.example.nedemo.models.Message
import com.example.nedemo.repositories.*
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/admin")
class MessageAdminController(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository,
) {
    private val crud: GenericDAO<Message, Long> = GenericDAO(messageRepository)

    fun getMinNumber(chat: Chat): Long {
        return messageRepository.findAll()
            .filter { it.chat.nameChat == chat.nameChat }
            .count().toLong()
    }

    @GetMapping("/messages")
    fun getItem(model: Model): String {
        model.addAttribute("items", crud.getAll().sortedBy { it.id })
        return "admin/message/index"
    }

    @GetMapping("/message/create")
    fun getModelCreate(model: Model): String {
        model.addAttribute("item", Message())
        model.addAttribute("chats", chatRepository.findAll())
        return "admin/message/create"
    }

    @PostMapping("/message/create")
    fun createModel(model: @Valid Message?, result: BindingResult, modelMap: ModelMap?): String {
        if (result.hasErrors()) {
            return "admin/message/create"
        }
        if (model != null) {
            model.numberMessage = getMinNumber(model.chat)
            crud.add(model)
            return "redirect:/admin/messages"
        }
        else{
            return "admin/message/create"
        }
    }

    @GetMapping("/message/{id}/edit")
    fun editProfile(model: Model, @PathVariable id: Long) : String {
        val item = crud.findById(id)
        if (item != null) {
            model.addAttribute("item", item)
            model.addAttribute("chats", chatRepository.findAll())
        }
        return "admin/message/edit"
    }

    @PostMapping("/message/{id}/edit")
    fun update(@ModelAttribute("item") item: Message, @PathVariable id: Long): String {
        crud.update(item)
        return "redirect:/admin/messages"
    }

    @PostMapping("/message/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        crud.deleteById(id)
        return "redirect:/admin/messages"
    }
}