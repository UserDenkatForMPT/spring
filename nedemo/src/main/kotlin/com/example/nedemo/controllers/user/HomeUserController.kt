package com.example.nedemo.controllers.user

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class HomeUserController {

    @GetMapping("/user", "/user/index")
    fun index(@RequestParam(name = "login", required = false) login: String?, model: Model): String {
        if (login != null) {
            model.addAttribute("login", login)
        }

        return "user/index"
    }
}