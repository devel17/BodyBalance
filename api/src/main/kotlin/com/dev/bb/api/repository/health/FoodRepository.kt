package com.dev.bb.repo

import com.dev.bb.api.enum.FoodType
import com.dev.bb.model.Food
import org.springframework.data.repository.CrudRepository

interface FoodRepository: CrudRepository<Food, Long> {
    fun findByType(name: FoodType): List<Food>?
}