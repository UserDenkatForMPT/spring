package com.example.nedemo.controllers.user


import com.example.nedemo.GenericDAO
import com.example.nedemo.models.Chat
import com.example.nedemo.models.User
import com.example.nedemo.repositories.*
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@Controller
@RequestMapping("/user")
class ChatUserController(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val modelRepository: ModelRepository,
    private val moduleRepository: ModuleRepository,
) {
    private val crud: GenericDAO<Chat, Long> = GenericDAO(chatRepository)
    private var user: User = User()

    @GetMapping("/chats")
    fun getItem(@RequestParam(name = "login", required = false) login: String?, model: Model): String {
        user = userRepository.findByLogin(login)!!
        model.addAttribute("items", crud.getAll().sortedBy { it.id })
        return "user/chat/index"
    }

    @GetMapping("/chat/create")
    fun getModelCreate(model: Model): String {
        model.addAttribute("item", Chat())
        model.addAttribute("models", modelRepository.findAll())
        model.addAttribute("modules", moduleRepository.findAll())
        return "user/chat/create"
    }

    @PostMapping("/chat/create")
    fun createModel(@Valid model: Chat?,
                    @RequestParam("modules1") modules1: List<Long>?,
                    @RequestParam("modules2") modules2: List<Long>?,
                    @RequestParam("modules3") modules3: List<Long>?,
                    result: BindingResult,
                    modelMap: ModelMap?): String {
        if (result.hasErrors()) {
            return "user/chat/create"
        }
        if (model != null) {
            val allModules = listOfNotNull(modules1, modules2, modules3).flatten().distinct()
            val selectedModules = moduleRepository.findAllById(allModules)
            model.modules.addAll(selectedModules)
            model.user = user

            crud.add(model)
            return "redirect:/user/chats?login=${user.login}"
        } else {
            return "user/chat/create"
        }
    }


    @GetMapping("/chat/{id}/edit")
    fun editProfile(model: Model, @PathVariable id: Long): String {
        val item = crud.findById(id)
        if (item != null) {
            model.addAttribute("item", item)
            model.addAttribute("models", modelRepository.findAll())
            model.addAttribute("allModules", moduleRepository.findAll())
            model.addAttribute("chatModules", item.modules)
        }
        return "user/chat/edit"
    }


    @PostMapping("/chat/{id}/edit")
    fun update(@ModelAttribute("item") item: Chat, @PathVariable id: Long): String {
        item.user = user
        item.updateTimeChat = LocalDate.now().toString()
        crud.update(item)
        return "redirect:/user/chats?login=${user.login}"
    }

    @PostMapping("/chat/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        crud.deleteById(id)
        return "redirect:/user/chats?login=${user.login}"
    }
}