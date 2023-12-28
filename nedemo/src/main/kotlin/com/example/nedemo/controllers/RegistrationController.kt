package com.example.nedemo.controllers


import com.example.nedemo.models.User
import com.example.nedemo.repositories.RoleRepository
import com.example.nedemo.repositories.TarifRepository
import com.example.nedemo.repositories.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RegistrationController(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val tarifRepository: TarifRepository,
) {

    @GetMapping("/registration")
    fun getRegistration(model: Model): String {
        model.addAttribute("user", User())
        return "reg"
    }

    @PostMapping("/registration")
    fun postRegistration(
        @ModelAttribute("user") user: User,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "reg"
        }
        userRepository.save(user.apply {
            this.password = BCryptPasswordEncoder().encode(password)
            this.role = roleRepository.findById(2).orElse(null)
            this.tarif = tarifRepository.findById(1).orElse(null)
        })
        return "redirect:/"
    }
}