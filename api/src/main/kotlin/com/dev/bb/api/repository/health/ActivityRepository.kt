package com.dev.bb.repo

import com.dev.bb.model.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : JpaRepository<Activity, Long> {
    // Здесь можно добавить дополнительные методы запросов, если они нужны
    // Например:
    // fun findByName(name: String): List<Activity>
    // fun findByCaloriesBurnedGreaterThan(calories: Int): List<Activity>
}
