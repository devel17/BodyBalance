package com.dev.bb.model

import com.dev.bb.api.model.enum.FoodType
import jakarta.persistence.Entity
import jakarta.persistence.Transient

@Entity
class Profile(
    var name: String,
    var weight: Double ,
    var fatPersent: Double,
    var proteinPerKilogram: Double,
    var fatPerKilogram: Double,
): Domain() {
    //default Profile( "devel's profile", 100.0, 32, 2.0, 1.5)
    constructor() : this("",0.0,0.0, 0.0, 0.0) {}

    @get:Transient
    var kkal: Double = 0.0 //general exchange
        get() = (weight - weight*fatPersent/100) * BASE_KKAL_KOEFFICIENT


    companion object {
        //1g proteing + 1g fat + 4g carbon per 1kg weight of body
        const val BASE_KKAL_KOEFFICIENT: Int = 1*4+1*9+4*4
    }
}