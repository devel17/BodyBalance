package com.dev.bb.api.service

import com.dev.bb.api.model.system.Menu
import com.dev.bb.api.repository.system.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuService(private val menuRepository: MenuRepository) {

    fun getAllMenus(): List<Menu> {
        return menuRepository.findAll()
    }

    fun createMenu(menu: Menu): Menu {
        return menuRepository.save(menu)
    }

    // Добавьте другие методы по мере необходимости
} 