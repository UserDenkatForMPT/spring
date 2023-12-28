package com.example.nedemo.controllers.employee


import com.example.nedemo.GenericDAO
import com.example.nedemo.models.Tarif
import com.example.nedemo.repositories.TarifRepository
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/employee")
class TarifEmployeeController(
    private val repository: TarifRepository,
) {
    private val crud: GenericDAO<Tarif, Long> = GenericDAO(repository)

    @GetMapping("/tarifes")
    fun getItem(model: Model): String {
        model.addAttribute("items", crud.getAll().sortedBy { it.id })
        return "employee/tarif/index"
    }

    @GetMapping("/tarif/create")
    fun getModelCreate(model: Model): String {
        model.addAttribute("item", Tarif())
        return "employee/tarif/create"
    }

    @PostMapping("/tarif/create")
    fun createModel(model: @Valid Tarif?, result: BindingResult, modelMap: ModelMap?): String {
        if (result.hasErrors()) {
            return "employee/tarif/create"
        }
        if (model != null) {
            crud.add(model)
            return "redirect:/employee/tarifes"
        }
        else{
            return "employee/tarif/create"
        }
    }

    @GetMapping("/tarif/{id}/edit")
    fun editProfile(model: Model, @PathVariable id: Long) : String {
        val item = crud.findById(id)
        if (item != null) {
            model.addAttribute("item", item)
        }
        return "employee/tarif/edit"
    }

    @PostMapping("/tarif/{id}/edit")
    fun update(@ModelAttribute("item") item: Tarif, @PathVariable id: Long): String {
        crud.update(item)
        return "redirect:/employee/tarifes"
    }

    @PostMapping("/tarif/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        crud.deleteById(id)
        return "redirect:/employee/tarifes"
    }
}