package com.dev.bb.api.repository.system

import com.dev.bb.api.model.system.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuRepository : JpaRepository<Menu, Long> {
    // Здесь можно добавить дополнительные методы для поиска, если это необходимо
} 