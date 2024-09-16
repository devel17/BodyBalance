package com.dev.bb.api.controller.plus

import com.dev.bb.model.Food
import com.dev.bb.model.Profile
import com.dev.bb.repo.FoodRepository
import com.dev.bb.service.FoodCombineService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/food")
@Tag(name ="Контроллер управления продуктами питания", description ="CRUD операции")
class FoodController(private val foodRepository: FoodRepository) {

    @PostMapping(consumes = ["application/json"])
    @Operation(summary = "Создание продукта", description = "200")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Продукт успешно создан"),
        ApiResponse(responseCode = "400", description = "Переданы неверные данные",
            content = [Content()]
        ),
        ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = [Content(examples = [ExampleObject(value = INTERNAL_SERVER_ERROR_OBJECT)])])
    ])
    fun create(@Valid @RequestBody food: Food) : ResponseEntity<String> = ResponseEntity(foodRepository.save(food).id.toString(), HttpStatus.OK)

    @GetMapping
    @Operation(summary = "Получение всех продуктов")
    fun getAll() = foodRepository.findAll()

    @GetMapping("/{id}")
    @Operation(summary = "Получение продукта")
    fun getOne( @PathVariable id: Long) =
        foodRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This food does not exist")


    companion object {
        const val INTERNAL_SERVER_ERROR_OBJECT: String = """
        {
            "code": 500,
            "message": "General Error",
            "caused": "java.sql.SQLException: Field 'count_per_day' doesn't have a default value"
        }
        """
    }


}