package com.dev.bb.api.controller.plus

import com.dev.bb.model.Profile
import com.dev.bb.service.FoodCombineService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/combine")
@Tag(name ="Контроллер генерации рациона", description ="Генерация рациона питания")
class CombineController(private val foodService: FoodCombineService) {

    @GetMapping("/compose/{profileId}")
    @Operation(summary = "Генерация меню по профайлу пользователя", description = "Метод генерирует хэш таблицу различных рационов питания , в качестве ключа общая каллорийность, исходя из введеных данных пользователя, учитывая его базовый обмен в заданных пределах" )
    fun compose(@PathVariable profileId: Long)= foodService.combine(profileId)

}