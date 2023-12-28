package com.example.nedemo.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthController {

    @GetMapping("/auth")
    fun index() : String {
        return "auth"
    }
}