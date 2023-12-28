package com.example.nedemo.controllers.employee


import com.example.nedemo.GenericDAO
import com.example.nedemo.models.Module
import com.example.nedemo.repositories.ModuleRepository
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/employee")
class ModuleEmployeeController(
    private val repository: ModuleRepository,
) {
    private val crud: GenericDAO<Module, Long> = GenericDAO(repository)

    @GetMapping("/modules")
    fun getItem(model: Model): String {
        model.addAttribute("items", crud.getAll().sortedBy { it.id })
        return "employee/module/index"
    }

    @GetMapping("/module/create")
    fun getModelCreate(model: Model): String {
        model.addAttribute("item", Module())
        return "employee/module/create"
    }

    @PostMapping("/module/create")
    fun createModel(model: @Valid Module?, result: BindingResult, modelMap: ModelMap?): String {
        if (result.hasErrors()) {
            return "employee/module/create"
        }
        if (model != null) {
            crud.add(model)
            return "redirect:/employee/modules"
        }
        else{
            return "employee/module/create"
        }
    }

    @GetMapping("/module/{id}/edit")
    fun editProfile(model: Model, @PathVariable id: Long) : String {
        val item = crud.findById(id)
        if (item != null) {
            model.addAttribute("item", item)
        }
        return "employee/module/edit"
    }

    @PostMapping("/module/{id}/edit")
    fun update(@ModelAttribute("item") item: Module, @PathVariable id: Long): String {
        crud.update(item)
        return "redirect:/employee/modules"
    }

    @PostMapping("/module/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        crud.deleteById(id)
        return "redirect:/employee/modules"
    }
}