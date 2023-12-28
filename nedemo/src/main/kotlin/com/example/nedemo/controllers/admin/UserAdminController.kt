package com.example.nedemo.controllers.admin


import com.example.nedemo.GenericDAO
import com.example.nedemo.models.User
import com.example.nedemo.repositories.RoleRepository
import com.example.nedemo.repositories.TarifRepository
import com.example.nedemo.repositories.UserRepository
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/admin")
class UserAdminController(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val tarifRepository: TarifRepository,
) {
    private val crud: GenericDAO<User, Long> = GenericDAO(userRepository)

    @GetMapping("/users")
    fun getItems(model: Model): String {
        model.addAttribute("items", crud.getAll().sortedBy { it.id })
        return "admin/user/index"
    }

    @GetMapping("/user/create")
    fun getItemCreate(model: Model): String {
        model.addAttribute("item", User())
        model.addAttribute("roles", roleRepository.findAll())
        model.addAttribute("tarifs", tarifRepository.findAll())
        return "admin/user/create"
    }

    @PostMapping("/user/create")
    fun createModel(model: @Valid User?, result: BindingResult, modelMap: ModelMap?): String {
        if (result.hasErrors()) {
            return "admin/user/create"
        }
        if (model != null) {
            crud.add(model)
            return "redirect:/admin/users"
        }
        else{
            return "admin/user/create"
        }
    }

    @GetMapping("/user/{id}/edit")
    fun editProfile(model: Model, @PathVariable id: Long) : String {
        val item = crud.findById(id)
        if (item != null) {
            model.addAttribute("item", item)
            model.addAttribute("roles", roleRepository.findAll())
            model.addAttribute("tarifs", tarifRepository.findAll())
        }
        return "admin/user/edit"
    }

    @PostMapping("/user/{id}/edit")
    fun update(@ModelAttribute("item") item: User, @PathVariable id: Long): String {
        crud.update(item)
        return "redirect:/admin/users"
    }

    @PostMapping("/user/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        crud.deleteById(id)
        return "redirect:/admin/users"
    }
}