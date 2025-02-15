package com.dev.bb.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "activities")
data class Activity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank(message = "Название активности не может быть пустым")
    @Column(nullable = false)
    var name: String,

    @field:Positive(message = "Продолжительность должна быть положительным числом")
    @Column(nullable = false)
    var duration: Int, // продолжительность в минутах

    @field:Positive(message = "Количество калорий должно быть положительным числом")
    @Column(nullable = false)
    var caloriesBurned: Int,

    @Column(nullable = true)
    var description: String? = null
)
