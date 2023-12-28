package com.example.nedemo.controllers.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeAdminController {

    @GetMapping("/admin", "/admin/index")
    fun index() : String {
        return "admin/index"
    }
}