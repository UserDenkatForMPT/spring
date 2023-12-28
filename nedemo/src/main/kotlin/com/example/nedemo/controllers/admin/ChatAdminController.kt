package com.example.nedemo.controllers.admin


import com.example.nedemo.GenericDAO
import com.example.nedemo.models.Chat
import com.example.nedemo.repositories.*
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@Controller
@RequestMapping("/admin")
class ChatAdminController(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val modelRepository: ModelRepository,
    private val moduleRepository: ModuleRepository,
) {
    private val crud: GenericDAO<Chat, Long> = GenericDAO(chatRepository)

    @GetMapping("/chats")
    fun getItem(model: Model): String {
        model.addAttribute("items", crud.getAll().sortedBy { it.id })
        return "admin/chat/index"
    }

    @GetMapping("/chat/create")
    fun getModelCreate(model: Model): String {
        model.addAttribute("item", Chat())
        model.addAttribute("users", userRepository.findAll())
        model.addAttribute("models", modelRepository.findAll())
        model.addAttribute("modules", moduleRepository.findAll())
        return "admin/chat/create"
    }

    @PostMapping("/chat/create")
    fun createModel(@Valid model: Chat?,
                    @RequestParam("modules1") modules1: List<Long>?,
                    @RequestParam("modules2") modules2: List<Long>?,
                    @RequestParam("modules3") modules3: List<Long>?,
                    result: BindingResult,
                    modelMap: ModelMap?): String {
        if (result.hasErrors()) {
            return "admin/chat/create"
        }
        if (model != null) {
            val allModules = listOfNotNull(modules1, modules2, modules3).flatten().distinct()
            val selectedModules = moduleRepository.findAllById(allModules)
            model.modules.addAll(selectedModules)

            crud.add(model)
            return "redirect:/admin/chats"
        } else {
            return "admin/chat/create"
        }
    }


    @GetMapping("/chat/{id}/edit")
    fun editProfile(model: Model, @PathVariable id: Long): String {
        val item = crud.findById(id)
        if (item != null) {
            model.addAttribute("item", item)
            model.addAttribute("users", userRepository.findAll())
            model.addAttribute("models", modelRepository.findAll())
            model.addAttribute("allModules", moduleRepository.findAll())  // Добавляем список всех модулей
            model.addAttribute("chatModules", item.modules)  // Добавляем список модулей, привязанных к чату
        }
        return "admin/chat/edit"
    }


    @PostMapping("/chat/{id}/edit")
    fun update(@ModelAttribute("item") item: Chat, @PathVariable id: Long): String {
        crud.update(item.apply { item.updateTimeChat = LocalDate.now().toString() })
        return "redirect:/admin/chats"
    }

    @PostMapping("/chat/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        crud.deleteById(id)
        return "redirect:/admin/chats"
    }
}