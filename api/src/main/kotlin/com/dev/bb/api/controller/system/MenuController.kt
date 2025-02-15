package com.dev.bb.api.controller.system

import com.dev.bb.api.components.KeycloakAdminClient
import com.dev.bb.api.enum.DepartmentList
import com.dev.bb.api.model.system.Menu
import com.dev.bb.api.service.MenuService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/system/menu")
@Tag(name ="Контроллер управления меню", description ="CRUD операции")
class MenuController(val keycloakAdminClient: KeycloakAdminClient, private val menuService: MenuService){

    @GetMapping("/header")
    @Operation(summary = "Получение элементов верхего меню для пользователя")
    fun getHeaderMenuItemsByUser(@RequestHeader("X-User-Id") userName: String) : List<DepartmentList> {
        return DepartmentList.values().filter{keycloakAdminClient.getUserGroups(userName).map{it.name.uppercase()}.toList().contains(it.name)}
    }

    @GetMapping("/left")
    @Operation(summary = "Получение элементов бокового меню для пользователя")
    fun getLeftMenuItemsByUser(@RequestHeader("X-User-Id") userName: String) : List<DepartmentList> {
        return DepartmentList.values().filter{keycloakAdminClient.getUserGroups(userName).map{it.name.uppercase()}.toList().contains(it.name)}
    }

    @GetMapping("/all")
    fun getAllMenus(): List<Menu> {
        return menuService.getAllMenus()
    }

    @PostMapping
    fun createMenu(@RequestBody menu: Menu): Menu {
        return menuService.createMenu(menu)
    }

}