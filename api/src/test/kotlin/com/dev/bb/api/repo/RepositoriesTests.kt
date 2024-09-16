package com.dev.bb.api.repo

import com.dev.bb.api.model.enum.FoodType
import com.dev.bb.model.Food
import com.dev.bb.repo.FoodRepository
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@DataJpaTest
@ActiveProfiles("test")
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val foodRepository: FoodRepository) {

    @Test
    fun `food_get_by_type`() {
        val food = Food(1.0, 1.0, 1.0, FoodType.MAIN)
        entityManager.persist(food)
        entityManager.flush()
        val found = foodRepository.findByType(food.type!!)?.get(0)
        assertThat(found).isEqualTo(food)
    }


}