package com.dev.bb.api.controller.minus

import com.dev.bb.model.Activity
import com.dev.bb.repo.ActivityRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/activity")
@Tag(name = "Контроллер управления активностями", description = "CRUD операции для активностей")
class ActivityController(private val activityRepository: ActivityRepository) {

    @PostMapping
    @Operation(summary = "Создание активности", description = "Создает новую активность")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Активность успешно создана"),
        ApiResponse(responseCode = "400", description = "Неверные данные", content = [Content()])
    ])
    fun create(@Valid @RequestBody activity: Activity): ResponseEntity<String> =
        ResponseEntity(activityRepository.save(activity).id.toString(), HttpStatus.OK)

    @GetMapping
    @Operation(summary = "Получение всех активностей", description = "Возвращает список всех активностей")
    fun getAll() = activityRepository.findAll()

    @GetMapping("/{id}")
    @Operation(summary = "Получение активности по ID", description = "Возвращает активность по указанному ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Активность найдена"),
        ApiResponse(responseCode = "404", description = "Активность не найдена")
    ])
    fun getOne(@Parameter(description = "ID активности") @PathVariable id: Long) =
        activityRepository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Активность не найдена") }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление активности", description = "Обновляет существующую активность")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Активность успешно обновлена"),
        ApiResponse(responseCode = "400", description = "Неверные данные", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Активность не найдена")
    ])
    fun update(@Parameter(description = "ID активности") @PathVariable id: Long, @Valid @RequestBody activity: Activity): Activity {
        if (!activityRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Активность не найдена")
        }
        activity.id = id
        return activityRepository.save(activity)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление активности", description = "Удаляет активность по указанному ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Активность успешно удалена"),
        ApiResponse(responseCode = "404", description = "Активность не на��дена")
    ])
    fun delete(@Parameter(description = "ID активности для удаления") @PathVariable id: Long) {
        if (!activityRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Активность не найдена")
        }
        activityRepository.deleteById(id)
    }
}
