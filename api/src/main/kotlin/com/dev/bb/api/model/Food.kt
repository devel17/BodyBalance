/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.bb.model

import com.dev.bb.api.model.enum.FoodType
import jakarta.persistence.Entity
import java.util.*

/**
 *
 * @author devel
 */
@Entity
class Food(
    var carbon: Double,
    var protein: Double,
    var fat: Double,
    var type: FoodType
): Domain(), Cloneable {

    var kkal: Long = 0
        get() = Math.round(this.protein * 4 + (this.carbon * 4) + (this.fat * 9))

    var name: String = this.type.desc + Math.round(Math.random() * 10)

    constructor() : this(0.0,0.0,0.0, FoodType.entries.random())

    @Throws(CloneNotSupportedException::class)
    fun plus(addedFood: Food): Food {
        val rez = clone() as Food
        rez.carbon += addedFood.carbon
        rez.protein += addedFood.protein
        rez.fat += addedFood.fat
        return rez
    }

    override fun toString(): String {
        return name + "(" + this.kkal + ":Б-" + Math.round(protein) + ",Ж-" + Math.round(
            fat
        ) + ",У-" + Math.round(carbon) + ")"
    }

    companion object {
        fun rand() = Food(
                Math.random() * 100 / 3,
                Math.random() * 100 / 3,
                Math.random() * 100 / 5,
                FoodType.values().random()
            )
    }
}