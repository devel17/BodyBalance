package com.dev.bb.api.model.system

import com.dev.bb.api.controller.system.MenuController
import com.dev.bb.api.repository.system.MenuRepository
import com.dev.bb.api.service.MenuService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.get
import com.fasterxml.jackson.databind.ObjectMapper

@WebMvcTest(MenuController::class)
@AutoConfigureMockMvc(addFilters = false)
class MenuTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var menuService: MenuService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return all menus`() {
        // given
        val menu1 = Menu(name = "Menu 1", description = "Description 1", root = true)
        val menu2 = Menu(name = "Menu 2", description = "Description 2", root = false)

        `when`(menuService.getAllMenus()).thenReturn(listOf(menu1, menu2))

        // when & then
        mockMvc.get("/admin/menu/all") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.size()") { value(2) }
                jsonPath("$[*].name") { exists() }
                jsonPath("$[*].description") { exists() }
            }
        }
    }

    @Test
    fun `should create new menu when valid data`() {
        // given
        val newMenu = Menu(name = "New Menu", description = "New Description", root = true)

        `when`(menuService.createMenu(any())).thenReturn(newMenu)

        // when & then
        mockMvc.post("/admin/menu") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newMenu)
        }.andExpect {
            status { isCreated() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.name") { value(newMenu.name) }
                jsonPath("$.description") { value(newMenu.description) }
            }
        }
    }

}