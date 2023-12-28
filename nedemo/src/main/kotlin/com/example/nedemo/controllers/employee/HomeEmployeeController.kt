package com.example.nedemo.controllers.employee

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class HomeEmployeeController {

    @GetMapping("/employee", "/employee/index")
    fun index(model: Model): String {
        return "employee/index"
    }
}