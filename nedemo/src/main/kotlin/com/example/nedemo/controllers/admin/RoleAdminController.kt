package com.example.nedemo.controllers.admin


import com.example.nedemo.GenericDAO
import com.example.nedemo.models.Role
import com.example.nedemo.models.Tarif
import com.example.nedemo.repositories.RoleRepository
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/admin")
class RoleAdminController(
    private val repository: RoleRepository,
) {
    private val crud: GenericDAO<Role, Long> = GenericDAO(repository)

    @GetMapping("/roles")
    fun getItem(model: Model): String {
        model.addAttribute("items", crud.getAll().sortedBy { it.id })
        return "admin/role/index"
    }

    @GetMapping("/role/create")
    fun getModelCreate(model: Model): String {
        model.addAttribute("item", Tarif())
        return "admin/role/create"
    }

    @PostMapping("/role/create")
    fun createModel(model: @Valid Role?, result: BindingResult, modelMap: ModelMap?): String {
        if (result.hasErrors()) {
            return "admin/role/create"
        }
        if (model != null) {
            crud.add(model)
            return "redirect:/admin/roles"
        }
        else{
            return "admin/role/create"
        }
    }

    @GetMapping("/role/{id}/edit")
    fun editProfile(model: Model, @PathVariable id: Long) : String {
        val item = crud.findById(id)
        if (item != null) {
            model.addAttribute("item", item)
        }
        return "admin/role/edit"
    }

    @PostMapping("/role/{id}/edit")
    fun update(@ModelAttribute("item") item: Role, @PathVariable id: Long): String {
        crud.update(item)
        return "redirect:/admin/roles"
    }

    @PostMapping("/role/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        crud.deleteById(id)
        return "redirect:/admin/roles"
    }
}