package com.example.nedemo.controllers.admin


import com.example.nedemo.GenericDAO
import com.example.nedemo.repositories.ModelRepository
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/admin")
class ModelAdminController(
    private val repository: ModelRepository,
) {
    private val crud: GenericDAO<com.example.nedemo.models.Model, Long> = GenericDAO(repository)

    @GetMapping("/models")
    fun getItem(model: Model): String {
        model.addAttribute("items", crud.getAll().sortedBy { it.id })
        return "admin/model/index"
    }

    @GetMapping("/model/create")
    fun getModelCreate(model: Model): String {
        model.addAttribute("item", com.example.nedemo.models.Model())
        return "admin/model/create"
    }

    @PostMapping("/model/create")
    fun createModel(model: @Valid com.example.nedemo.models.Model?, result: BindingResult, modelMap: ModelMap?): String {
        if (result.hasErrors()) {
            return "admin/model/create"
        }
        if (model != null) {
            crud.add(model)
            return "redirect:/admin/models"
        }
        else{
            return "admin/model/create"
        }
    }

    @GetMapping("/model/{id}/edit")
    fun editProfile(model: Model, @PathVariable id: Long) : String {
        val item = crud.findById(id)
        if (item != null) {
            model.addAttribute("item", item)
        }
        return "admin/model/edit"
    }

    @PostMapping("/model/{id}/edit")
    fun update(@ModelAttribute("item") item: com.example.nedemo.models.Model, @PathVariable id: Long): String {
        crud.update(item)
        return "redirect:/admin/models"
    }

    @PostMapping("/model/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        crud.deleteById(id)
        return "redirect:/admin/models"
    }
}