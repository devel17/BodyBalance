package com.dev.bb.api.controller.common

import com.dev.bb.api.repo.ProfileRepository
import com.dev.bb.model.Profile
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/profile")
@Tag(name ="Контроллер управления данными пользователя", description ="CRUD операции")
class ProfileController(val profileRepository: ProfileRepository) {

    @GetMapping
    @Operation(summary = "Получение профайла пользователя")
    fun getAll(): Nothing = TODO()

    @PostMapping
    @Operation(summary = "Создание профайла пользователя")
    fun create(profile: Profile) = profileRepository.save(profile);

}