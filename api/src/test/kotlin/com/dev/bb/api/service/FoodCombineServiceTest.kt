package com.dev.bb.service

import com.dev.bb.api.model.enum.FoodType
import com.dev.bb.api.repo.ProfileRepository
import com.dev.bb.model.Food
import com.dev.bb.model.Profile
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.util.Optional
import java.util.stream.Stream


@SpringBootTest
@ActiveProfiles("test")
class FoodCombineServiceTest(@Autowired val foodService: FoodCombineService) {

    @MockBean
    lateinit var profileRepository: ProfileRepository

    val food : Food = Food.rand()
    val foods : List<Food> = Stream.generate{Food.rand()}.limit(10).toList()

    @Test
    fun `init random Food`() {
        println(food.takeIf { it -> it.protein > 0 })
        assertNotNull(food)
        assertTrue(food.protein > 0)
    }

    @Test
    fun `getting_kkal_from_food`() {
        println(food.also { println(it) }.kkal)
    }

    @Test
    fun `test_hash_map`() {
        println(food.type)
        var map = hashMapOf<Long?, Food>()
        map[food.id] = food
    }

    @Test
    fun `get_grouping_by_type`() {
        foods.groupBy(Food::type).forEach(::println)
    }

    @Test
    fun `check_foods_by_limit`() {
        val limits = mapOf(FoodType.MAIN to 20, FoodType.FIRST to 10, FoodType.SECOND to 20, FoodType.FRUIT_AND_VEGITABLES to 30)
        foods.groupBy(Food::type).forEach { println("$it : ${limits[it.key]}") }
        foods.also(::println).groupBy(Food::type).all {it -> it.value.size < limits[it.key]!! }.also {println("$it")}
    }

    @RepeatedTest(1)
    fun `combine_foods_by profile`(repetitionInfo: RepetitionInfo, testInfo: TestInfo) {
        `when`(profileRepository.findById(1)).then { Optional.of(Profile("devel's profile", 100.0, 32.1, 2.0, 1.5)) }
        var rez = foodService.combine(1)
        println(rez?.entries?.size)
        rez?.entries?.forEach { println( "${it.key} : ${it.value}") }
        //assertTrue(rez?.entries?.size!! > 0)
    }
}