package com.dev.bb.api.controller

import com.dev.bb.api.model.enum.FoodType
import com.dev.bb.api.controller.plus.FoodController
import com.dev.bb.model.Food
import com.dev.bb.repo.FoodRepository
import com.dev.bb.service.FoodCombineService
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(FoodController::class)
class FoodControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var foodRepository: FoodRepository

    @MockBean
    lateinit var foodService: FoodCombineService

    val FOOD1 : Food = Food(1.0,1.0,1.0,FoodType.FRUIT_AND_VEGITABLES)
    val FOOD2 : Food = Food(2.0,2.0,2.0,FoodType.MAIN)

    @Test
    fun findAll() {
        `when`(foodRepository.findAll()).thenReturn(listOf(FOOD1, FOOD2))
        mockMvc.get("/api/food") {
            contentType = MediaType.APPLICATION_JSON
            //content = mapper.writeValueAsString(input)
            accept = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"

        }.andExpect {
            status { MockMvcResultMatchers.status().isOk }
            content {
                contentTypeCompatibleWith("application/json")
                jsonPath("$.[*].kkal") {
                    value(Matchers.notNullValue())
                    value(Matchers.containsInAnyOrder(34,17))
                }
                jsonPath("$.[*].type") {
                    value(Matchers.notNullValue())
                    value(Matchers.containsInAnyOrder(FoodType.FRUIT_AND_VEGITABLES.name, FoodType.MAIN.name))
                }
                jsonPath("$.size()"){
                    value(2)
                }
                jsonPath("$.[0].protein") {
                    isNumber()
                    value(1.0)
                    value(Matchers.oneOf(1.0,2.0))
                }
            }
            content { contentType(MediaType.APPLICATION_JSON) }
            //content { json(mapper.writeValueAsString(expectation)) }
        } .andDo {
            print()
            handle {
                println(it.response.characterEncoding)
            }
        }
    }

    @Test
    fun findOne() {
    }

    @Test
    fun generate() {
    }
}