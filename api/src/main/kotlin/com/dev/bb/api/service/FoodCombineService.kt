package com.dev.bb.service

import com.dev.bb.api.exception.custom.NotFoundEntityException
import com.dev.bb.api.model.enum.FoodType
import com.dev.bb.api.repo.ProfileRepository
import com.dev.bb.model.Food
import com.dev.bb.model.Profile
import com.dev.bb.repo.FoodRepository
import org.paukov.combinatorics3.Generator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.math.abs

@Service
class FoodCombineService(@Autowired val foodRepository: FoodRepository, val profileRepository: ProfileRepository):
    AFoodCombineService() {

    val FOOD_BUSKET_SIZE: Long = 20
    val DELTA_KKAL_LIMIT: Int = 50
    val DELTA_PROTEIN_LIMIT: Int = 7
    val DELTA_FAT_LIMIT: Int = 5

    val DEFAULT_MEAL_PLAN = mapOf(FoodType.MAIN to 2, FoodType.FIRST to 1, FoodType.SECOND to 3, FoodType.FRUIT_AND_VEGITABLES to 50, FoodType.SNACK to 30)

    var calculatedFoodMap = mutableMapOf<Number, List<Food>>()

    override fun combine(profileId: Long): Map<Number, List<Food>> {
        val foods = foodRepository.findAll().toList().ifEmpty { Stream.generate(Food::rand).limit(FOOD_BUSKET_SIZE).toList() }
        val profile = profileRepository.findById(profileId).orElseThrow { throw NotFoundEntityException("Profile with id = $profileId not found!" ) }.also { println(it.kkal) }
        Generator.subset(foods)
            .simple()
            .stream()
            .collect(Collectors.toList<List<Food>>())
            .forEach { item ->
                if (isDeviatedFromBaseExchange(profile, item)) {
                    calculatedFoodMap[item.stream().mapToLong { o -> o.kkal }.sum()] = item
                }
            }
        return calculatedFoodMap
    }

    private fun isDeviatedFromBaseExchange(profile: Profile, foods: List<Food>): Boolean {
        val proteinWeight: Double = (profile.weight - profile.fatPersent * profile.weight / 100) * profile.proteinPerKilogram
        val fatWeight: Double = (profile.weight - profile.fatPersent * profile.weight / 100) * profile.fatPerKilogram
        val food: Food = union(foods)
        return abs(food.kkal - profile.kkal) < DELTA_KKAL_LIMIT && abs(food.protein - proteinWeight) < DELTA_PROTEIN_LIMIT
                && abs(food.fat - fatWeight) < DELTA_FAT_LIMIT && foods.groupBy(Food::type).all {it -> it.value.size < DEFAULT_MEAL_PLAN[it.key]!! }
    }

    private fun union(foods: List<Food>): Food {
        val rez = Food()
        rez.protein = foods.stream().mapToDouble { o: Food -> o.protein }.sum()
        rez.fat = foods.stream().mapToDouble { o: Food -> o.fat }.sum()
        rez.carbon = foods.stream().mapToDouble { o: Food -> o.carbon }.sum()
        rez.name = foods.size.toString()
        return rez
    }
}

